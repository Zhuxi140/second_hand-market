local hashKey = KEYS[1]
local expire = tonumber(ARGV[1])
local fieldValues = {}

for  i = 2, #ARGV do
    table.insert(fieldValues, ARGV[i])
end


redis.call('HSET',hashKey,unpack(fieldValues))
redis.call('EXPIRE',hashKey,expire)

return 1