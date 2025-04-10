local failed = {}
local total = #KEYS

for i = 1, total do
  local stock = tonumber(redis.call("GET", KEYS[i]))
  local quantity = tonumber(ARGV[i])
  local menuId = ARGV[total + i]

  if stock == nil then
    table.insert(failed, "MISSING:" .. menuId)
  elseif stock < quantity then
    table.insert(failed, "INSUFFICIENT:" .. menuId)
  end
end

if #failed > 0 then
  return failed
end

for i = 1, total do
  redis.call("DECRBY", KEYS[i], ARGV[i])
end

return {"SUCCESS"}