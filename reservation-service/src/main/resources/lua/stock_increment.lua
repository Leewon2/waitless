local teamCountKey = KEYS[1]
local totalMenus = tonumber(ARGV[1])

-- 팀 수 감소 (최소 0 유지)
local currentTeamCount = tonumber(redis.call("GET", teamCountKey) or "0")
if currentTeamCount > 0 then
    redis.call("DECR", teamCountKey)
end

-- stock 원복
for i = 1, totalMenus do
    local qty = tonumber(ARGV[(i - 1) * 2 + 2])
    local menuId = ARGV[(i - 1) * 2 + 3]
    local stockKey = KEYS[i + 1]
    if redis.call("EXISTS", stockKey) == 0 then
        return {"MISSING_STOCK", menuId}
    end
    redis.call("INCRBY", stockKey, qty)
end

return {"SUCCESS"}