#!/bin/bash

emoticons=(
    "<emoticon>emoticon_img_red_03</emoticon>"
    "<emoticon>emoticon_img_red_01</emoticon>"
    "<emoticon>emoticon_img_red_04</emoticon>"
    "<emoticon>emoticon_img_blue_03</emoticon>"
    "<emoticon>emoticon_img_blue_05</emoticon>"
    "<emoticon>emoticon_img_blue_06</emoticon>"
    "<emoticon>emoticon_img_red_06</emoticon>"
    "<emoticon>emoticon_img_red_02</emoticon>"
    "<emoticon>emoticon_img_blue_01</emoticon>"
    "<emoticon>emoticon_img_blue_02</emoticon>"
    "<emoticon>emoticon_img_blue_04</emoticon>"
    "<emoticon>emoticon_img_red_03</emoticon>"
    "<emoticon>emoticon_img_red_01</emoticon>"
    "<emoticon>emoticon_img_red_04</emoticon>"
)

# 영어 문자열 생성 함수
generate_random_english() {
    local CHARS="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    local LENGTH=$((RANDOM % 6 + 10))  # 10에서 15 사이의 숫자 생성
    local RESULT=""

    for (( i=0; i<$LENGTH; i++ )); do
        local INDEX=$((RANDOM % ${#CHARS}))
        RESULT+=${CHARS:$INDEX:1}
    done

    echo "$RESULT"
}

# 배열의 모든 이미모티콘을 순차적으로 처리하면서 랜덤 텍스트 추가
for emoticon in "${emoticons[@]}"
do
   # 랜덤 텍스트 생성
   random_text=$(generate_random_english)

   # 랜덤 텍스트와 이미모티콘 결합
   content="${emoticon}_${random_text}"

   curl -k -X POST \
     -H "Accept: */*" \
     -H "Connection: keep-alive" \
     -H "Accept-Encoding: gzip, deflate, br" \
     -H "Cookie: ASP.NET_SessionId=bjttou3g1ohrwdnch3gbt0x0; _ga=GA1.2.848346599.1708339012; _gid=GA1.2.819194153.1712627178; PCID=17083390115485266802788" \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -H "User-Agent: SuddenRadio/4.2.4 (iPhone; iOS 16.0; Scale/3.00)" \
     -H "Host: saradioapi.nexon.com" \
     -H "Accept-Language: ko-KR;q=1, en-KR;q=0.9" \
     -d "auth_code=331791BJ&content=$content&post_no=54&season_level_join_flag=Y&season_level_no=28&sns_user_sn=233123&user_level=47" \
     "https://saradioapi.nexon.com/Sns/PostCommentCreate.aspx"

  # 6초에서 8초 사이의 랜덤 대기 시간 생성
  wait_time=$(awk -v min=6 -v max=8 'BEGIN{srand(); print min + rand() * (max - min)}')

  # 랜덤 대기 시간 출력 및 sleep
  echo "Waiting for $wait_time seconds..."
  sleep $wait_time
done
