local zsetKey = KEYS[1]
local targetId = ARGV[1]
local newPosition = tonumber(ARGV[2])

local members = redis.call('ZRANGE', zsetKey, 0, -1)

local newList = {}
for i, member in ipairs(members) do
    if member ~= targetId then
        table.insert(newList, member)
    end
end

local finalList = {}
local inserted = false
for i = 1, #newList do
    if i == newPosition then
        table.insert(finalList, targetId)
        inserted = true
    end
    table.insert(finalList, newList[i])
end

if not inserted then
    table.insert(finalList, targetId)
end

redis.call('DEL', zsetKey)

for i = 1, #finalList do
    redis.call('ZADD', zsetKey, i, finalList[i])
end

return true