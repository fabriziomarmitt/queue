package queue;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Fluxo fluxo = new Fluxo();
        fluxo.adicionarGeradorRequisicoes(new GeradorRequisicoes() {{
            adicionarFonte(new Fonte() {{
                setDistribuicao(new Exponencial(1000));
                setTipo(TipoRequisicao.SIMPLES);
            }});
            adicionarFonte(new Fonte() {{
                setDistribuicao(new Exponencial(750));
                setTipo(TipoRequisicao.REGULAR);
            }});
            adicionarFonte(new Fonte() {{
                setDistribuicao(new Exponencial(100));
                setTipo(TipoRequisicao.COMPLEXA);
            }});
        }});
        fluxo.adicionarPasso(new Passo("AppServerRequest") {{
            adicionarServidor(new Servidor(0.5, "AppServerRequest1") {{
                adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 1000);
                adicionaTaxaDeServico(TipoRequisicao.REGULAR, 750);
                adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 100);
            }});
            adicionarServidor(new Servidor(0.5, "AppServerRequest2") {{
                adicionaTaxaDeServico(TipoRequisicao.SIMPLES, 1000);
                adicionaTaxaDeServico(TipoRequisicao.REGULAR, 750);
                adicionaTaxaDeServico(TipoRequisicao.COMPLEXA, 100);
            }});
        }});
        fluxo.adicionarPasso(new Passo("Database") {{
            adicionarServidor(new Servidor(0.5, "Database1"));
            adicionarServidor(new Servidor(0.5, "Database2"));
        }});
        fluxo.adicionarPasso(new Passo("AppServerResponse") {{
            adicionarServidor(new Servidor(0.5, "AppServerResponse1"));
            adicionarServidor(new Servidor(0.5, "AppServerResponse2"));
        }});
        fluxo.executar(100);
        fluxo.imprimeEstatisticas();
    }
}