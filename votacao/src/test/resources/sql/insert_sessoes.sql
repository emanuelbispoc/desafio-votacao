INSERT INTO pautas(id, descricao, data_criacao, associado_id) VALUES (1, 'Pauta Teste 1', '2020-05-20', 1);
INSERT INTO sessoes(id, data_inicio, data_fim, pauta_id) VALUES (1, '2023-12-12 13:02:52', '2099-05-20 13:02:52', 1);

INSERT INTO votos_sessao(decisao, associado_id, sessao_id) VALUES ('NAO', 2, 1);


INSERT INTO pautas(id, descricao, data_criacao, associado_id) VALUES (2, 'Pauta Teste 2', '2020-05-20', 1);
INSERT INTO sessoes(id, data_inicio, data_fim, pauta_id) VALUES (2, '2023-12-12 10:00:00', '2023-12-12 15:00:00', 2);


INSERT INTO votos_sessao(decisao, associado_id, sessao_id) VALUES ('SIM', 2, 2);
INSERT INTO votos_sessao(decisao, associado_id, sessao_id) VALUES ('SIM', 3, 2);
INSERT INTO votos_sessao(decisao, associado_id, sessao_id) VALUES ('NAO', 4, 2);

INSERT INTO pautas(id, descricao, data_criacao, associado_id) VALUES (3, 'Pauta Teste 3', '2020-05-20', 2);
INSERT INTO sessoes(id, data_inicio, data_fim, pauta_id) VALUES (3, '2023-12-12 14:00:00', '2023-12-13 09:00:00', 3);


INSERT INTO votos_sessao(decisao, associado_id, sessao_id) VALUES ('NAO', 1, 3);
INSERT INTO votos_sessao(decisao, associado_id, sessao_id) VALUES ('SIM', 2, 3);
INSERT INTO votos_sessao(decisao, associado_id, sessao_id) VALUES ('NAO', 4, 3);