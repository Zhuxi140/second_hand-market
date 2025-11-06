local key = KEYS[1]
local fields = ARGV

local result = {}

for i,field in ipairs(fields) do
  result[i] = redis.call('HGET', key, field)
end

return result