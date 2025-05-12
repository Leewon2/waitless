local teamKey = KEYS[1]
local reservationNumberKey = KEYS[2]
local teamLimit = tonumber(ARGV[1])
local totalMenus = tonumber(ARGV[2])
local failed = {}

local currentTeamCount = tonumber(redis.call("GET", teamKey) or "0")
if currentTeamCount + 1 > teamLimit then
  return {"TEAM_OVER"}
end

for i = 1, totalMenus do
  local qty = tonumber(ARGV[(i - 1) * 2 + 3])
  local menuId = ARGV[(i - 1) * 2 + 4]
  local stockKey = KEYS[i + 2]
  local stock = tonumber(redis.call("GET", stockKey))

  if stock == nil or stock < qty then
    table.insert(failed, menuId)
  end
end

if #failed > 0 then
  table.insert(failed, 1, "INSUFFICIENT")
  return failed
end

redis.call("INCR", teamKey)

for i = 1, totalMenus do
  redis.call("DECRBY", KEYS[i + 2], ARGV[(i - 1) * 2 + 3])
end

-- 예약 번호 발급
local reservationNumber = redis.call("INCR", reservationNumberKey)
return {"SUCCESS", tostring(reservationNumber)}