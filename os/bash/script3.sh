#!/bin/bash

# Написать скрипт, который переименовывает все файлы
# с именами, начинающиеся на «b». (команда mv)

# Ищем в текущем каталоге файлы с префиксом "b".
# При отсутствии выводится ошибка
files=`ls b* 2>/dev/null`
if [[ -z $files ]]; then echo Files dont exist ; exit; fi


for i in $files
do
	mv $i B$i
done
echo Done
