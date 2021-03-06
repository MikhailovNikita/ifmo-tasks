#!/bin/bash
# Написать скрипт, который создает много (порядка 100 штук)
# файлов в вашей рабочей директории с различными именами,
# среди которых есть имена начинающиеся на «a», на «b» и на «c».

# Генерируем 100 имена, состоящие из символов англ. алфавита и цифр, содеражащие 7 цифр
for i in `cat /dev/urandom | tr -dc "a-zA-Z0-9_" | fold -w 7 | head -n 100`
do
	 # Добавляем префикс 'a', 'b' или 'c'
	 echo $(($RANDOM % 3)) | tr 012 abc | xargs echo -n
	 echo $i
# Создаем файлы
done  | xargs touch
echo Done
