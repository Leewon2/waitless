local total = #KEYS
local failed = {}

for i = 1, total do
  local stock = tonumber(redis.call("GET", KEYS[i]))
  local quantity = tonumber(ARGV[(i - 1) * 2 + 1])
  local menuId = ARGV[(i - 1) * 2 + 2]

  if stock < quantity then
    table.insert(failed, menuId)
  end
end

if #failed > 0 then
  return failed
end

for i = 1, total do
  redis.call("DECRBY", KEYS[i], ARGV[(i - 1) * 2 + 1])
end

return {"SUCCESS"}