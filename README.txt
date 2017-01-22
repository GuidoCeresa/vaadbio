MySQL usare solo utf8mb4 con defaultcollation oppure bin oppure unicode-ci oppure general-ci
Bin è l'unico sensibile alle maiuscole ad agli accenti

Usare:
create database WIKI default character set utf8mb4 collate utf8mb4_bin

TEST
1) Database con collation utf8mb4_general_ci -> Create 3.486/3.486 nuove voci con commit di ogni singolo record in 77 sec.

2) Database con collation utf8mb4_bin -> Create 3.486/3.486 nuove voci con commit di ogni singolo record in 60 sec.