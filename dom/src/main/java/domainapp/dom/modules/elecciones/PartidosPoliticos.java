package domainapp.dom.modules.elecciones;

import com.google.common.collect.Maps;
import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.HighchartsColor;
import com.googlecode.wickedcharts.highcharts.options.color.NullColor;
import com.googlecode.wickedcharts.highcharts.options.color.RadialGradient;
import com.googlecode.wickedcharts.highcharts.options.functions.PercentageFormatter;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.value.Blob;
import org.isisaddons.module.excel.dom.ExcelService;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by German on 01/04/2015.
 */
@DomainService(repositoryFor = PartidoPolitico.class)
@DomainServiceLayout(menuOrder = "30")
public class PartidosPoliticos {
    //region > listAll (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Listado de Partidos")
    @MemberOrder(sequence = "1")
    public List<PartidoPolitico> listAll() {
        QueryDefault<PartidoPolitico> query = QueryDefault.create(PartidoPolitico.class,"find");
        return container.allMatches(query);
    }
    //endregion

    //region > listAll (action)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(named = "Listado de Partidos", hidden = Where.ANYWHERE)
    public List<PartidoPolitico> listByName(final String nombrePartido) {
        QueryDefault<PartidoPolitico> query = QueryDefault.create(PartidoPolitico.class,"findByNombre","nombrePartido", nombrePartido);
        return container.allMatches(query);
    }
    //endregion

    //region > injected services

    //region > create (action)
    @MemberOrder(sequence = "2")
    @ActionLayout(named = "Crear Partido")
    @Action(invokeOn = InvokeOn.COLLECTION_ONLY )
    public PartidoPolitico create(
            final @ParameterLayout(named="Nombre Agrupacion Politica")String nombre) {

        final PartidoPolitico pp = container.newTransientInstance(PartidoPolitico.class);
        pp.setNombrePartido(nombre);
        container.persistIfNotAlready(pp);
        return pp;
    }

    //endregion

     @Action(
            semantics = SemanticsOf.SAFE
    )
    public WickedChart showPieChart() {

        Map<String, BigDecimal> byPartido = Maps.newTreeMap();
        List<PartidoPolitico> allPartidos =listAll();
        for (PartidoPolitico partido : allPartidos) {
            String nombrePartido = partido.getNombrePartido();
            BigDecimal integer = partido.getTotalBocaDeUrna();
            if(integer == null) {
                integer = new BigDecimal(1);
            }
            byPartido.put(nombrePartido, BigDecimal.valueOf(50));

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

    @javax.inject.Inject
    DomainObjectContainer container;

    //endregion
}
