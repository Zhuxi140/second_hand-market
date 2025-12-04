local hashKey = KEYS[1]
local isHostData = KEYS[2]== "1"
local expire = tonumber(ARGV[1])
local fieldValues = {}

for  i = 2, #ARGV do
    table.insert(fieldValues, ARGV[i])
end

redis.call('HSET',hashKey,unpack(fieldValues))

if not isHostData and expire and expire > 0 then
    redis.call('EXPIRE',hashKey,expire)
end

return 1