package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.AccountTypeMeasureBean;
import com.tnbank.agentui.beans.RequestDayMeasureBean;
import com.tnbank.agentui.beans.TransactionDayMeasureBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.addon.charts.*;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.Collection;
import java.util.Comparator;

class GraphSheet extends TabSheet {
    private VerticalLayout accountsAnalytics = new VerticalLayout();
    private VerticalLayout transactionsAnalytics = new VerticalLayout();
    private Collection<AccountTypeMeasureBean> accountTypeMeasureBeans;
    private Collection<RequestDayMeasureBean> requestDayMeasureBeans;
    private Collection<TransactionDayMeasureBean> transactionDayMeasureBeans;

    GraphSheet() {
        accountTypeMeasureBeans = Services.getEtlProxy().findAllAccountMeasures().getContent();
        requestDayMeasureBeans = Services.getEtlProxy().findAllRequestMeasures().getContent();
        transactionDayMeasureBeans = Services.getEtlProxy().findAllTransactionMeasures().getContent();

        addAccountsChart();

        addTab(accountsAnalytics, "Accounts");
        addTab(transactionsAnalytics, "Transactions");
    }

    private void addAccountsChart() {
        Chart accountsChart = new Chart();
        Configuration accountsChartConf = accountsChart.getConfiguration();
        accountsChartConf.setTitle("Accounts Analytics");
        accountsChartConf.setExporting(true);
        accountsChartConf.getExporting().setWidth(800);

        XAxis x = new XAxis();
        x.setCategories(
                String.valueOf(accountTypeMeasureBeans
                        .stream()
                        .sorted(Comparator.comparing(AccountTypeMeasureBean::getDay))
                        .map(AccountTypeMeasureBean::getDay))
        );
        accountsChartConf.addxAxis(x);
        Style labelStyle = new Style();
        labelStyle.setTop("8px");
        labelStyle.setLeft("40px");
        accountsChartConf.setLabels(new HTMLLabels(labelStyle, new HTMLLabelItem("Total Accounts Distribution")));

        DataSeries series = new DataSeries();
        PlotOptionsColumn plotOptions = new PlotOptionsColumn();
        series.setPlotOptions(plotOptions);
        series.setName("NORM");
//        series.setData(
//                (Number) accountTypeMeasureBeans
//                        .stream()
//                        .mapToInt(value -> Math.toIntExact(value.getTodayNormCount()))
//        );
        series.setData(1,2,3,4,5);
        accountsChartConf.addSeries(series);

        series = new DataSeries();
        plotOptions = new PlotOptionsColumn();
        series.setPlotOptions(plotOptions);
        series.setName("NENT");
//        series.setData(
//                (Number) accountTypeMeasureBeans
//                        .stream()
//                        .mapToInt(value -> Math.toIntExact(value.getTodayNentCount()))
//        );
        series.setData(1,2,3,4,5);
        accountsChartConf.addSeries(series);

        series = new DataSeries();
        plotOptions = new PlotOptionsColumn();
        series.setPlotOptions(plotOptions);
        series.setName("PREM");
//        series.setData(
//                (Number) accountTypeMeasureBeans
//                        .stream()
//                        .mapToInt(value -> Math.toIntExact(value.getTodayPremCount()))
//        );
        series.setData(1,2,3,4,5);
        accountsChartConf.addSeries(series);

        series = new DataSeries();
        plotOptions = new PlotOptionsColumn();
        series.setPlotOptions(plotOptions);
        series.setName("PENT");
//        series.setData(
//                (Number) accountTypeMeasureBeans
//                        .stream()
//                        .mapToInt(value -> Math.toIntExact(value.getTodayPentCount()))
//        );
        series.setData(1,2,3,4,5);
        accountsChartConf.addSeries(series);

        series = new DataSeries();
        PlotOptionsSpline splinePlotOptions = new PlotOptionsSpline();
        Marker marker = new Marker();
        marker.setWidth(2);
        marker.setLineColor(new SolidColor("black"));
        marker.setFillColor(new SolidColor("white"));
        splinePlotOptions.setMarker(marker);
        splinePlotOptions.setColor(new SolidColor("black"));
        series.setPlotOptions(splinePlotOptions);
        series.setName("Average");
//        series.setData(
//                (Number) accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getAllCount),
//                accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getTodayCount).sum()
//        );
        series.setData(1,2,3,4,5);
        accountsChartConf.addSeries(series);

        series = new DataSeries();
        series.setPlotOptions(new PlotOptionsPie());
        series.setName("Total consumption");
        DataSeriesItem item = new DataSeriesItem(
                "NORM",
                accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getTodayNormCount).sum()
                        + accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getAllNormCount).sum()
        );
        series.add(item);
        item = new DataSeriesItem(
                "NENT",
                accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getTodayNentCount).sum()
                        + accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getAllNentCount).sum()
        );
        series.add(item);
        item = new DataSeriesItem(
                "PREM",
                accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getTodayPremCount).sum()
                        + accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getAllPremCount).sum()
        );
        series.add(item);
        item = new DataSeriesItem(
                "PENT",
                accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getTodayPentCount).sum()
                        + accountTypeMeasureBeans.stream().mapToLong(AccountTypeMeasureBean::getAllPentCount).sum()
        );
        series.add(item);
        PlotOptionsPie plotOptionsPie = new PlotOptionsPie();
        plotOptionsPie.setSize("100px");
        plotOptionsPie.setCenter("100px", "80px");
        plotOptionsPie.setShowInLegend(false);
        plotOptionsPie.setShowInLegend(false);
        series.setPlotOptions(plotOptionsPie);
        accountsChartConf.addSeries(series);

        accountsChart.drawChart();
        //accountsAnalytics.addComponent(accountsChart);
    }

    void reset() {
        accountsAnalytics.removeAllComponents();
        transactionsAnalytics.removeAllComponents();
    }
}
