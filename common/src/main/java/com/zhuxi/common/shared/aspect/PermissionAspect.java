package com.zhuxi.common.shared.aspect;

import com.zhuxi.common.domain.service.PermissionCacheService;
import com.zhuxi.common.interfaces.result.PermissionInfo;
import com.zhuxi.common.shared.annotation.PermissionCheck;
import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.PermissionException;
import com.zhuxi.common.shared.exception.TokenException;
import com.zhuxi.common.shared.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * @author zhuxi
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PermissionAspect {

    private final JwtUtils jwtUtils;
    private final PermissionCacheService permissionCacheService;

    @Around("@annotation(permissionCheck)")
   public Object checkPermission(ProceedingJoinPoint joinPoint, PermissionCheck permissionCheck) throws Throwable {

        HttpServletRequest request = getRequest();
        if (request == null) {
            log.error("checkPermission:getRequest()-error : request is null.");
            throw new TokenException(AuthMessage.REQUEST_CONTEXT_NOT_FOUND);
        }

        // 解析必须数据信息
        Claims claims = getTokenClaims(request);
        String role = (String)claims.get("role");
        Role tokenRole = Role.valueOf(role);
        String sn = null;
        if (Role.user.equals(permissionCheck.Role())) {
            sn = (String) claims.get("userSn");
        }else if(Role.admin.equals(permissionCheck.Role())){
            sn = (String) claims.get("adminSn");
        }

        // 效验权限
        log.debug("CheckRole starting");
        checkPermission(tokenRole,sn,permissionCheck);

        // 效验数据所有者
        if (permissionCheck.enableDataOwnership()) {
            checkSn(sn,joinPoint,permissionCheck);
            log.debug("Check end");
            return joinPoint.proceed();
        }

        log.debug("Check end");
        return joinPoint.proceed();
    }


    // 权限效验
    private void checkPermission(Role tokenRole,String sn, PermissionCheck permissionCheck){
        if (tokenRole.getId() >= Role.admin.getId()){
            //待完善
        }
        // 获取权限信息
        PermissionInfo permissionInfo = permissionCacheService.getPermissionInfo(sn);
        if (permissionInfo == null || permissionInfo.getPermissionCode() == null){
            log.warn("checkPermission:getRoleLevel()-warn : permissionInfo is null or no permissionCode found for userSn :{}.", sn);
            throw new PermissionException(AuthMessage.NO_PERMISSION);
        }

        boolean validRole = checkRole(tokenRole, permissionCheck.Role(),Role.getRoleById(permissionInfo.getRole()));
        boolean validPermission = checkPermissionCode(permissionInfo.getPermissionCode(),permissionCheck.permission());
        boolean checkPassed;

        if (permissionCheck.logic().equals(PermissionCheck.Logic.AND)){
            checkPassed = validRole && validPermission;
        }else{
            checkPassed = validRole || validPermission;
        }

        if (!checkPassed){
            log.error("""
                    checkPermission:check failed:no permission.
                    checkRole:{} checkPermission:{}
                    Logic: {}
                    requiredRole:{}, deliverRole:{}
                    requiredPermission:{}, RoleOrAccount have :{}
                    """,
                    validRole, validPermission, permissionCheck.logic(),
                    permissionCheck.Role(), tokenRole,
                    permissionCheck.permission(), permissionInfo.getPermissionCode()
            );
            throw new PermissionException(AuthMessage.NO_PERMISSION);
        }
    }


    private boolean checkPermissionCode(List<String> permissionCode, String requiredPermission){
        return permissionCode.contains(requiredPermission);
    }

    private boolean checkRole(Role tokenRole,Role requiredRole,Role permissionRole){
        return tokenRole.equals(requiredRole) && permissionRole.equals(requiredRole);
    }

   private void checkSn(String sn,ProceedingJoinPoint joinPoint, PermissionCheck permissionCheck){
       String argsUserSn = getArgsUserSn(joinPoint, permissionCheck.snParam());

       if (argsUserSn == null || sn ==  null){
           log.warn("checkSn:getRoleLevel()-warn : argsUserSn:{} or tokenUserSn:{} is null.", argsUserSn, sn);
           throw new PermissionException(AuthMessage.NO_PERMISSION);
       }
       log.debug("CheckSn starting");
       if(!sn.equals(argsUserSn)){
           log.error("checkSn:getRoleLevel()-error : tokenUserSn is not equal to argsUserSn.");
           throw new PermissionException(AuthMessage.NO_PERMISSION);
       }
   }

   private HttpServletRequest getRequest(){
       ServletRequestAttributes rA = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       return rA == null ? null : rA.getRequest();
   }

   private Claims getTokenClaims(HttpServletRequest request){
       String auth = request.getHeader("Authorization");
       if (auth == null || auth.isBlank()){
           throw new TokenException(AuthMessage.LOGIN_INVALID);
       }
       String token = auth.replaceAll("(?i)Bearer\\s*", "");
       Claims claims = jwtUtils.parseUserToken(token);
       if (claims == null){
           log.error("getTokenClaims:getRoleLevel()-error : claims is null.");
           throw new TokenException(AuthMessage.LOGIN_INVALID);
       }
       return claims;
   }

   private String getArgsUserSn(ProceedingJoinPoint joinPoint,String paramName){
       Object[] args = joinPoint.getArgs();
       MethodSignature signature = (MethodSignature)joinPoint.getSignature();
       String[] parameterNames = signature.getParameterNames();
       for (int i = 0; i < parameterNames.length; i++){
           if (parameterNames[i].equals(paramName) && args[i] instanceof String){
               return (String)args[i];
           }
       }
       return null;
   }
}
