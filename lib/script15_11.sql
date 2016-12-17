show databases;

use fit;

show tables;

desc alunos;


desc av_fisica;


alter table alunos add column av_fisica_cadastrada bool after codavfisica;

alter table alunos modify column plano varchar(40);

alter table alunos modify column cpf varchar(15);

/** tirar unique*/
ALTER TABLE alunos DROP INDEX cpf;

alter table alunos modify column rg varchar(15) unique;

alter table alunos modify column nome varchar(45);

alter table alunos modify column av_fisica_cadastrada bool default '0';

alter table alunos drop column valor_mensalidade;

alter table alunos add column dia_pagamento int after plano;

alter table alunos add column data_ultimo_pagamento date after dia_pagamento;

update alunos set pagamento = '1' where cod_matricula = '82077';

alter table alunos drop column avfisica;

select av_fisica_cadastrada from alunos where cod_matricula = '21100';


update alunos set av_fisica_cadastrada = '1' where cod_matricula = '37689';

UPDATE av_fisica SET altura = '1.55', peso = '88.88' WHERE cod_matricula = '38912';

select * from alunos;

select * from alunos  WHERE dia_pagamento < day(now()) AND MONTH(NOW()) = MONTH(data_ultimo_pagamento);

select * from alunos where dia_pagamento = '5' and pagamento = '0';


select MONTH(NOW());

update alunos set pagamento = '1' where dia_pagamento = '15' && dia_pagamento = '10';

update alunos set pagamento = '1' where dia_pagamento <= day(now());

UPDATE alunos SET data_ultimo_pagamento = null;



select day(now());

select now();

select * from alunos where pagamento = '0';










/**********Funcionários***************/

use fit;

create table funcionarios(
cod_funcionario int not null,
nome varchar (30) not null,
sexo enum ('M' , 'F'),
rg varchar(16),
cpf varchar(16),
objetivo varchar (50),
formacao varchar (50),
primary key (cod_funcionario)
);

desc funcionarios;

select * from funcionarios;

alter table funcionarios add column salario decimal(10,2) after formacao;

delete from funcionarios where cod_funcionario = '87135';

