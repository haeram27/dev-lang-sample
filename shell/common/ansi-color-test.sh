#!/bin/zsh

colortest-style() {
  for style in {0..9}; do
    printf "\033[${style};36;47mstyle:${style}\033[0m ";
    echo
  done
}


colortest-3bit() {
  for fg in {30..37}; do
    for bg in {40..47}; do
      printf "\033[${fg};${bg}mf:${fg},b:${bg}\033[0m ";
    done
    echo
  done
  echo

  for fg in {90..97}; do
    for bg in {40..47}; do
      printf "\033[${fg};${bg}mf:${fg},b:${bg}\033[0m ";
    done
    echo
  done
  echo

  for fg in {30..37}; do
    for bg in {100..107}; do
      printf "\033[${fg};${bg}mf:${fg},b:${bg}\033[0m ";
    done
    echo
  done
  echo

  for fg in {90..97}; do
    for bg in {100..107}; do
      printf "\033[${fg};${bg}mf:${fg},b:${bg}\033[0m ";
    done
    echo
  done
  echo
}


colortest-8bit() {
  echo "foreground:"
  for fgcolor in {1..15};do
    printf "\e[38;5;${fgcolor}m%5d\033[0m" ${fgcolor}
  done
  echo

  for fgcolor in {16..231};do
    if [[ (${fgcolor} -ne 16) && ($((${fgcolor}%6)) -eq 4) ]];then echo ""; fi
    printf "\e[38;5;${fgcolor}m%5d\033[0m" ${fgcolor}
  done
  echo

  for fgcolor in {232..255};do
    printf "\e[38;5;${fgcolor}m%5d\033[0m" ${fgcolor}
  done
  echo
  echo

  echo "background:"
  for bgcolor in {1..15};do
    printf "\e[48;5;${bgcolor}m%5d\033[0m" ${bgcolor}
  done
  echo

  for bgcolor in {16..231};do
    if [[ (${bgcolor} -ne 16) && ($((${bgcolor}%6)) -eq 4) ]];then echo ""; fi
    printf "\e[48;5;${bgcolor}m%5d\033[0m" ${bgcolor}
  done
  echo

  for bgcolor in {232..255};do
    printf "\e[48;5;${bgcolor}m%5d\033[0m" ${bgcolor}
  done
  echo
  echo

  echo "combination:"
  print "\e[38;5;196;48;5;21m combination \033[0m"
}


colortest-tput-8bit() {
  echo "foreground:"
  for color in {1..15};do
    printf  "$(tput setaf ${color})%5d$(tput sgr0)" ${color}
  done
  echo

  for color in {16..231};do
    if [[ (${color} -ne 16) && ($((${color}%6)) -eq 4) ]];then echo ""; fi
    printf  "$(tput setaf ${color})%5d$(tput sgr0)" ${color}
  done
  echo

  for color in {232..255};do
    printf  "$(tput setaf ${color})%5d$(tput sgr0)" ${color}
  done
  echo
  echo

  echo "background:"
  for color in {1..15};do
    printf  "$(tput setab ${color})%5d$(tput sgr0)" ${color}
  done
  echo

  for color in {16..231};do
    if [[ (${color} -ne 16) && ($((${color}%6)) -eq 4) ]];then echo ""; fi
    printf  "$(tput setab ${color})%5d$(tput sgr0)" ${color}
  done
  echo

  for color in {232..255};do
    printf  "$(tput setab ${color})%5d$(tput sgr0)" ${color}
  done
  echo
  echo

  echo "combination:"
  print "$(tput setaf 196 setab 21) combination $(tput sgr0)"
}


colortest-8bit
