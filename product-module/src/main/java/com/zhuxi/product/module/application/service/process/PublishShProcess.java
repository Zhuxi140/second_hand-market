package com.zhuxi.product.module.application.service.process;

import com.zhuxi.product.module.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
public class PublishShProcess {

    private final ProductRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> getOtherNames(Long conditionId, Long categoryId) {
        String categoryName = repository.getCategoryNameById(categoryId);
        String conditionName = repository.gerConditionNameById(conditionId);

        return List.of(categoryName, conditionName);
    }
}
