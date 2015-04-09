/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.app.homepage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.google.common.collect.Maps;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Cursor;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.HighchartsColor;
import com.googlecode.wickedcharts.highcharts.options.color.NullColor;
import com.googlecode.wickedcharts.highcharts.options.color.RadialGradient;
import com.googlecode.wickedcharts.highcharts.options.functions.PercentageFormatter;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import domainapp.dom.modules.elecciones.*;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;

import org.apache.isis.applib.annotation.*;


@ViewModel
public class HomePageViewModel {

    //region > title
    public String title() {
        return  " Panel de Control";
    }
    //endregion

  /* @Action(
            semantics = SemanticsOf.SAFE
    )
    public WickedChart getPieChart() {

        Map<String, BigDecimal> byPartido = Maps.newTreeMap();
        List<PartidoPolitico> allPartidos = partidos.listAll();
        for (PartidoPolitico partido : allPartidos) {
            String nombrePartido = partido.getNombrePartido();
            BigDecimal integer = partido.getTotalBocaDeUrna();
            if(integer == null) {
                integer = new BigDecimal(1);
            }
            byPartido.put(nombrePartido, integer);

        }

        return new WickedChart(new PieWithGradientOptions(byPartido));
    }

    public static class PieWithGradientOptions extends Options {
        private static final long serialVersionUID = 1L;

        public PieWithGradientOptions(Map<String, BigDecimal> byCategory) {

            setChartOptions(new ChartOptions()
                    .setPlotBackgroundColor(new NullColor())
                    .setPlotBorderWidth(null)
                    .setPlotShadow(Boolean.FALSE));

            setTitle(new Title("Votos por Partido"));

            PercentageFormatter formatter = new PercentageFormatter();
            setTooltip(
                    new Tooltip()
                            .setFormatter(
                                    formatter)
                            .       setPercentageDecimals(1));

            setPlotOptions(new PlotOptionsChoice()
                    .setPie(new PlotOptions()
                            .setAllowPointSelect(Boolean.TRUE)
                            .setCursor(Cursor.POINTER)
                            .setDataLabels(new DataLabels()
                                    .setEnabled(Boolean.TRUE)
                                    .setColor(new HexColor("#000000"))
                                    .setConnectorColor(new HexColor("#000000"))
                                    .setFormatter(formatter))));

            Series<Point> series = new PointSeries()
                    .setType(SeriesType.PIE);
            int i=0;
            for (Map.Entry<String, BigDecimal> entry : byCategory.entrySet()) {
                series
                        .addPoint(
                                new Point(entry.getKey(), entry.getValue()).setColor(
                                        new RadialGradient()
                                                .setCx(0.5)
                                                .setCy(0.3)
                                                .setR(0.7)
                                                .addStop(0, new HighchartsColor(i))
                                                .addStop(1, new HighchartsColor(i).brighten(-0.3f))));
                i++;
            }
            addSeries(series);
        }
    }
*/
   @Action(
            semantics = SemanticsOf.SAFE
    )
     public List<Fiscal> getFiscales() {
        return fiscales.listAll();
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    public WickedChart getGraficoPorPartido() {

        return partidos.showPieChart();
    }

  //region > injected services

    @javax.inject.Inject
    PartidosPoliticos partidos;

    @javax.inject.Inject
    PartidoPolitico partido;

    @javax.inject.Inject
    Candidatos candidatos;

    @javax.inject.Inject
    Fiscales fiscales;
 //endregion
}
