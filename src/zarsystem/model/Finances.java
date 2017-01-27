package zarsystem.model;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import zarsystem.controller.MenuController;

/**
 * Classe base do controle do grafico de Finanças
 * Created by joaov on 09/12/2016.
 */
public class Finances {
    MenuController menuController;
    BarChart<String, Double> chartVendas;
    BarChart<String, Double> chartLucro;

    public Finances(MenuController menuController) {
        this.menuController = menuController;
        chartVendas         = menuController.barChartVendas;
        chartLucro          = menuController.barChartLucro;
    }

    /**
     * Carrega o gráfico de finanças
     * */
    public void loadChart(){
        System.out.println("Carregando gráficos...");

        //Limpar gráficos antes de preenchê-los
        chartVendas.getData().clear();
        chartLucro.getData().clear();


        //Gráfico de vendas
        XYChart.Series<String, Double> seriesVendas = new XYChart.Series<>();
        seriesVendas.setName("Vendas Efetuadas");
        seriesVendas.getData().add(new XYChart.Data<>("Vendas Efetuadas", Double.parseDouble(menuController.lblFinancasVendasEfetuadas.getText())));
        chartVendas.getData().add(seriesVendas);

        XYChart.Series<String, Double> series2Vendas = new XYChart.Series<>();
        series2Vendas.setName("Estoque Registrado");
        series2Vendas.getData().add(new XYChart.Data<>("Estoque Registrado", Double.parseDouble(menuController.lblFinancasEstoqueRegistrado.getText())));
        chartVendas.getData().add(series2Vendas);

        //Gráfico de lucro
        XYChart.Series<String, Double> seriesLucro = new XYChart.Series<>();
        seriesLucro.setName("Valor Gasto");
        seriesLucro.getData().add(new XYChart.Data<>("Gastos", Double.parseDouble(menuController.lblFinancasValorGasto.getText())));
        chartLucro.getData().add(seriesLucro);

        XYChart.Series<String, Double> series2Lucro = new XYChart.Series<>();
        series2Lucro.setName("Valor Arrecadado");
        series2Lucro.getData().add(new XYChart.Data<>("Arrecadação", Double.parseDouble(menuController.lblFinancasValorArrecadado.getText())));
        chartLucro.getData().add(series2Lucro);

        XYChart.Series<String, Double> series3Lucro = new XYChart.Series<>();
        series3Lucro.setName("Lucro previsto com base no estoque");
        series3Lucro.getData().add(new XYChart.Data<>("Previsão de lucro", Double.parseDouble(menuController.lblFinancasLucroFinal.getText())));
        chartLucro.getData().add(series3Lucro);

        XYChart.Series<String, Double> series4Lucro = new XYChart.Series<>();
        series4Lucro.setName("lucro obtido até o momento");
        series4Lucro.getData().add(new XYChart.Data<>("Lucro até agora", Double.parseDouble(menuController.lblFinancasLucroAteAgora.getText())));
        chartLucro.getData().add(series4Lucro);
    }
}
