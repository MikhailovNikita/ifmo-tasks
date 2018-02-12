#!/bin/bash

# Написать скрипт, который копирует под новым именем все
# файлы, имеющие имена начинающиеся на «с». (команда cp)

# Ищем в текущем каталоге файлы с префиксом "с".
# При отсутствии выводится ошибка
files=`ls c* 2>/dev/null`
if [[ -z $files ]]; then echo Files dont exist; exit; fi

for i in $files
do
	cp $i C$i
done
echo Done
