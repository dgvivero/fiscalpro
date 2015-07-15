package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;

import java.util.List;

/**
 * Created by German on 05/04/2015.
 */
@DomainService(repositoryFor = MesaElectoral.class)
@DomainServiceLayout(
        named="Mesas Electorales",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "4.4"
)
public class MesasElectorales {
 //region > listAll (action)
    @Action(semantics = SemanticsOf.SAFE )
    @ActionLayout( named = "Listado de Mesas Electorales" )
    @MemberOrder(sequence = "1")
    public List<MesaElectoral> listAll() {
        QueryDefault<MesaElectoral> query = QueryDefault.create(MesaElectoral.class,"find");
        return container.allMatches(query);
    }
 //endregion
 //region > listar por numero de mesa (action)
     @ActionLayout(named = "Buscar por Nro")
     @MemberOrder(sequence = "2")
     public List<MesaElectoral> listByNroMesa(final@ParameterLayout(named="Numero") Integer nro) {
         QueryDefault<MesaElectoral> query = QueryDefault.create( MesaElectoral.class,"findByNroMesa","nromesa",nro);
         List<MesaElectoral> mesas = container.allMatches(query);
         if(mesas.isEmpty()) {
             container.informUser("No hay ningun Circuito Electoral con numero:"+ nro);
         }
         return mesas;
     }
//endregion

//region > create (action)
 @MemberOrder(sequence = "3")
 @ActionLayout(named = "Crear Mesa Electoral")
 public MesaElectoral create(
         final @ParameterLayout(named = "Circuito") Integer circuito,
         final @ParameterLayout(named = "Numero de Mesa") Integer numeroMesa,
         final @ParameterLayout(named = "Nro") Integer nro) {

     final MesaElectoral mesa = container.newTransientInstance(MesaElectoral.class);
     Circuito c = container.firstMatch(Circuito.class, circuito.toString());
     mesa.getEstablecimiento().setCircuito(c);
     mesa.setNroMesa(numeroMesa);
     container.persistIfNotAlready(mesa);
     return mesa;
 }

//endregion

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    Circuitos circuitos;

 //endregion
}
