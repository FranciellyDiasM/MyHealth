# My Health: Aplicativo de Alimentação Saudável

Este projeto aborda conhecimentos do curso de Análise e Desenvolvimento de Sistemas. O aplicativo tem como objetivo promover hábitos saudáveis por meio da
alimentação, fornecendo aos usuários sugestões de alimentos e cardápios semanais personalizados. A personalização é baseada no Índice de Massa Corporal
(IMC) e/ou no objetivo individual de cada usuário, como ganho de massa muscular, perda de peso ou manutenção de uma alimentação equilibrada.


A alimentação tem um papel fundamental na saúde e bem-estar. Nosso aplicativo foi pensado para auxiliar os usuários a fazerem escolhas alimentares mais
conscientes e adequadas às suas necessidades, proporcionando praticidade e orientações nutricionais de maneira acessível e intuitiva.

* Disciplinas: Banco de dados e Projeto Integrador II
* Tema: Alimentação
* Guarda-chuva Temático: Saúde e Bem-Estar

## Funcionalidades
Cálculo de IMC: Com base nas informações fornecidas pelo usuário (peso e altura), o aplicativo calcula o IMC e sugere um plano alimentar adequado. Objetivos
Personalizados: O usuário pode escolher entre diferentes objetivos (ganho de massa, perda de peso, alimentação balanceada), e o aplicativo sugerirá cardápios
semanais de acordo com essas metas. Exibição de Cardápios: A interface permite a visualização de cardápios semanais, organizados de forma prática, com
sugestões de alimentos para todas as refeições do dia. Banco de Dados Local: Utilizamos o SQLite para armazenar localmente as informações do usuário e seus
cardápios personalizados. Interface Intuitiva e Amigável: Desenvolvida em Kotlin para Android, a interface foi projetada para ser fácil de usar e acessível para todos
os perfis de usuários.

## Tecnologias Utilizadas
* [Kotlin](https://kotlinlang.org/): Linguagem de programação utilizada para o desenvolvimento do aplicativo Android.
* [SQLite](https://www.sqlite.org/): Banco de dados local para armazenar informações sobre os usuários e cardápios personalizados.
* [GitHub](https://github.com/): Repositório para controle de versão e colaboração entre os membros da equipe.
* [Trello](https://trello.com/): Kanban para gestão de tarefas e visualização de progresso.


## Estrutura do Projeto
* [/app](https://github.com/FranciellyDiasM/MyHealth/tree/main/MyHealth): Contém o código-fonte do aplicativo desenvolvido em Kotlin.
* [/banco-dados](https://github.com/FranciellyDiasM/MyHealth/tree/main/banco-dados): Scripts de criação e manipulação do banco de dados SQLite.

# Como testar
Existe um matérial ensinando testar nas documentações: [docs/instalar.md](https://github.com/FranciellyDiasM/MyHealth/blob/main/docs/instalar.md)

## Colaboradores
FABIO BOEKER JUNIOR  
FRANCIELLY DIAS MACEDO  
JOÃO GUILHERME CRISTINO HEIDMANN  
JUAN TAVARES MARCOLINO LIRIO  
KAYKE RIOS DE PAULA  
PEDRO HENRIQUE DE FREITAS TESSNIARI  
RODRIGO SANTOS ROCHA  

## Arquivos importantes:
[Pasta com os DAOs do mongo](https://github.com/FranciellyDiasM/MyHealth/tree/main/MyHealth/app/src/main/java/br/com/quatrodcum/myhealth/model/dao/mongodb)  
[Pasta com os DAOs legado(Sqlite)](https://github.com/FranciellyDiasM/MyHealth/tree/main/MyHealth/app/src/main/java/br/com/quatrodcum/myhealth/model/dao/sqlite)

#### Exemplos de Data Access Object
[mongo/ObjectiveDao.kt](https://github.com/FranciellyDiasM/MyHealth/blob/main/MyHealth/app/src/main/java/br/com/quatrodcum/myhealth/model/dao/mongodb/ObjectiveDao.kt), interessante pois tem a exclusão em cascata  
[sqlite/ObjectiveDao.kt](https://github.com/FranciellyDiasM/MyHealth/blob/main/MyHealth/app/src/main/java/br/com/quatrodcum/myhealth/model/dao/sqlite/ObjectiveDao.kt)

Fluxo interessante na [SplashScreen](https://github.com/FranciellyDiasM/MyHealth/blob/main/MyHealth/app/src/main/java/br/com/quatrodcum/myhealth/controller/SplashScreenController.kt): caso o banco mongoDb esteja vazio ele carrega os dados do banco legado(possível observar no metodo `migrateFromSqlite` e a migração sendo feita no `loadFrom` do [mongodb/DatabaseDao.kt](https://github.com/FranciellyDiasM/MyHealth/blob/main/MyHealth/app/src/main/java/br/com/quatrodcum/myhealth/model/dao/mongodb/DatabaseDao.kt)


## Checkpoint
#### Relatórios:
<6.a.1.i> e <6.a.1.ii>
* Aplicativo possui splashscreen

#### Inserir Registors
<6.a.1.i> e <6.a.1.ii>
* Aplicação permite adicionar `objetivo`, `ingrediente`, `unidade de medida`, `refeição` e `usuario`
* Possui relacionamento 1..n: um usuario tem um objetivo e um objetivo pode ter varios usuarios
* Possui relacionamento n..n: uma refeicao possui uma lista de objetos que é o componente da refeicao, com ingrediente e unidade de medida(um ingrediente pode estar em várias refeições e uma refeição pode ter vários ingredientes)

#### Remover Registros
<6.c.[1-4 e 6]> e <6.c.5.i>
* A Aplicação permite remover qualquer registro
* Caso o registro não esteja sendo usado, exibe uma mensagem simples de confirmação
* Caso o registro esteja sendo usado, exibe uma mensagem alertando que se confirmar, vai apagar o registro e todos os outros que o usa

#### Atualizar Registros
<6.d.[1-6]> e <6.d.[7-8]>
* Todos os campos de todos os registros podem ser editados(exceto id)

#### Vídeo
O vídeo segue com a mesmas funcionalidades do app anterior

