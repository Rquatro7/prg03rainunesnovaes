# Atividade 23 - Spring Data

Saida do git diff --stat do commit que trocou os DAOs pelo Spring Data:

```
 prg03rainunesnovaes/pom.xml                        |  14 +--
 .../src/main/java/br/com/ifba/Main.java            |  56 ---------
 .../br/com/ifba/infrastructure/dao/GenericDao.java | 130 ---------------------
 .../com/ifba/infrastructure/dao/GenericIDao.java   |  18 ---
 .../infrastructure/util/ConfiguracaoConexao.java   |  60 ----------
 .../br/com/ifba/infrastructure/util/JPAUtil.java   |  36 ------
 .../ifba/infrastructure/util/MigracaoBanco.java    |  27 -----
 .../java/br/com/ifba/usuario/dao/UsuarioDao.java   |  51 --------
 .../java/br/com/ifba/usuario/dao/UsuarioIDao.java  |  16 ++-
 .../ifba/usuario/repository/UsuarioRepository.java |  50 ++++++++
 .../ifba/infrastructure/dao/GenericDaoTest.java    | 130 ---------------------
 .../com/ifba/usuario/dao/UsuarioDaoBancoTest.java  |  78 -------------
 .../repository/UsuarioRepositoryBancoTest.java     |  83 +++++++++++++
 13 files changed, 153 insertions(+), 596 deletions(-)
```

Total de linhas removidas: 596
