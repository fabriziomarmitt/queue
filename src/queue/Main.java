package queue;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        GeradorRequisicoes geradorRequisicoes = new GeradorRequisicoes() {{
            adicionarFonte(new Fonte() {{
                setDistribuicao(new Exponencial(250));
                setTipo(TipoRequisicao.SIMPLES);
            }});
            adicionarFonte(new Fonte() {{
                setDistribuicao(new Exponencial(600));
                setTipo(TipoRequisicao.REGULAR);
            }});
            adicionarFonte(new Fonte() {{
                setDistribuicao(new Exponencial(150));
                setTipo(TipoRequisicao.COMPLEXA);
            }});
        }};
        cenario2(geradorRequisicoes);
    }

    public static void cenario1(GeradorRequisicoes geradorRequisicoes) throws Exception {
        Fluxo fluxo = new Fluxo("Cenário 1");
        fluxo.adicionarGeradorRequisicoes(geradorRequisicoes);
        fluxo.adicionarSubFluxo(new SubFluxo("TodasRequisicoes", 1) {{
            adicionarPasso(new Passo("AppServerRequest") {{
                adicionarServidor(new Servidor("AppServerRequest1") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
                adicionarServidor(new Servidor("AppServerRequest2") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
                adicionarServidor(new Servidor("AppServerRequest2") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
            }});
        }});
        fluxo.executar(10000);
        fluxo.salvaEstatisticas();
    }

    public static void cenario2(GeradorRequisicoes geradorRequisicoes) throws Exception {
        Fluxo fluxo = new Fluxo("Cenario 2");
        fluxo.adicionarGeradorRequisicoes(geradorRequisicoes);
        fluxo.adicionarSubFluxo(new SubFluxo("Videos", 0.2345) {{
            adicionarPasso(new Passo("VideosAppLayer") {{
                adicionarServidor(new Servidor("VideoServer1") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
                adicionarServidor(new Servidor("VideoServer2") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
            }});
            adicionarPasso(new Passo("VideosDbLayer") {{
                adicionarServidor(new Servidor("VideoDBServer1") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
            }});
        }});
        fluxo.adicionarSubFluxo(new SubFluxo("Noticias", 0.6344) {{
            adicionarPasso(new Passo("NoticiasAppLayer") {{
                adicionarServidor(new Servidor("NotAppServer1") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
                adicionarServidor(new Servidor("NotAppServer2") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
                adicionarServidor(new Servidor("NotAppServer3") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
                adicionarServidor(new Servidor("NotAppServer4") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
            }});
            adicionarPasso(new Passo("NoticiasDbLayer") {{
                adicionarServidor(new Servidor("NoticiasDb1") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
                adicionarServidor(new Servidor("NoticiasDb2") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
            }});
        }});
        fluxo.adicionarSubFluxo(new SubFluxo("Streaming", 0.1311) {{
            adicionarPasso(new Passo("StreamingAppLayer") {{
                adicionarServidor(new Servidor("StreamingAppServer1") {{
                    adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 250);
                    adicionaTaxaDeServico(TipoRequisicao.REGULAR, 600);
                    adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 150);
                }});
            }});
        }});
        fluxo.executar(10000);
        fluxo.salvaEstatisticas();
    }


}