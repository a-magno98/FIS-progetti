create schema LATAZZASCHEMA;

create table LATAZZASCHEMA.personale(
  nome varchar(64) not null,
  primary key (nome)
);

create table LATAZZASCHEMA.pagamento_debito(

  nome varchar(64) not null,
  data TIMESTAMP default CURRENT_TIMESTAMP not null,
  euro decimal,
  primary key (nome, data),
);

create table LATAZZASCHEMA.vendita(

  nome varchar(64) not null,
  tipo_cialda varchar(64),
  numero_cialde integer not null check (numero_cialde > 0),
  data TIMESTAMP default CURRENT_TIMESTAMP not null,
  contanti boolean not null, -- se  false paga con credito e parte del personale, altrimenti in contanti
  primary key (data, nome),
);

create table LATAZZASCHEMA.Magazzino
(
  tipo varchar(64) not null primary key,
  qta_cialde integer default(0)
);

create table LATAZZASCHEMA.Rifornimento(

  dataR TIMESTAMP default CURRENT_TIMESTAMP not null,
  numero_scatole integer not null, 
  tipo_cialda varchar(64),
  primary key (dataR,tipo_cialda)
);

create table LATAZZASCHEMA.Debito(

  nome varchar(64) not null,
  euro decimal,
  attivo boolean not null,
  primary key (nome)

);

create table LATAZZASCHEMA.Cassa(
  euro decimal default (0)check(euro >= 0)
);

insert into LATAZZASCHEMA.Cassa values (default);
insert into LATAZZASCHEMA.Magazzino values ('caffè', default);
insert into LATAZZASCHEMA.Magazzino values ('caffèArabica', default);
insert into LATAZZASCHEMA.Magazzino values ('thè', default);
insert into LATAZZASCHEMA.Magazzino values ('thèLimone', default);
insert into LATAZZASCHEMA.Magazzino values ('cioccolata', default);
insert into LATAZZASCHEMA.Magazzino values ('camomilla', default);