local teamKey = KEYS[1]
local teamLimit = tonumber(ARGV[1]) -- 팀 한도
local totalMenus = tonumber(ARGV[2]) --  메뉴 갯수, 이 수만큼 반복문 돌면서 재고 확인
local failed = {} -- 실패한 메뉴 넣기

local currentTeamCount = tonumber(redis.call("GET", teamKey) or "0") -- 현재 팀 수 확인

-- 제한 초과 여부 체크
if currentTeamCount + 1 > teamLimit then
  return {"TEAM_OVER"}
end

-- 재고 확인
for i = 1, totalMenus do
  local qty = tonumber(ARGV[(i - 1) * 2 + 3]) -- 0일 떄 3, 1일 때 5. 메뉴 갯수 확인
  local menuId = ARGV[(i - 1) * 2 + 4]
  local stockKey = KEYS[i + 1]
  local stock = tonumber(redis.call("GET", stockKey))

  if stock == nil or stock < qty then
    table.insert(failed, menuId)
  end
end

-- 재고 부족 시 반환
if #failed > 0 then
  return failed
end

-- 모든 조건 통과 → 팀 수 증가하고 재고도 감소하기
redis.call("INCR", teamKey)
for i = 1, totalMenus do
  redis.call("DECRBY", KEYS[i + 1], ARGV[(i - 1) * 2 + 3])
end

return {"SUCCESS"}