package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;

import java.util.List;

/**
 * Created by German on 09/04/2015.
 */
@DomainService(repositoryFor = Seccion.class)
@DomainServiceLayout(
        named="Mesas Electorales",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "4.2"
)
public class Secciones {

//region > listAll (action)
    @Action(semantics = SemanticsOf.SAFE )
    @ActionLayout(named = "Listado")
    @MemberOrder(sequence = "1")
    public List<Seccion> listAll() {
        QueryDefault<Seccion> query = QueryDefault.create(Seccion.class,"find");
        return container.allMatches(query);
    }
//endregion
//region > listar por dni (action)
    @ActionLayout(named = "Buscar por numero seccion")
    @MemberOrder(sequence = "2")
    public List<Seccion> listByDNI(final@ParameterLayout(named="Numero de Seccion") Integer numero) {
        QueryDefault<Seccion> query = QueryDefault.create( Seccion.class,"findByNumber","nroSeccion",numero);
        List<Seccion> secciones = container.allMatches(query);
        if(secciones.isEmpty()) {
            container.informUser("No hay ninguna Seccion Electoral numero:"+ numero);
        }
        return secciones;
    }
//endregion

//region > addSeccion (action)

    @MemberOrder(sequence = "3")
    @ActionLayout(named = "crear Seccion")
    @Action(invokeOn=InvokeOn.OBJECT_AND_COLLECTION, semantics = SemanticsOf.SAFE )
    public Seccion create(
            @ParameterLayout(named = "Distrito") final Distrito distrito,
            @ParameterLayout(named = "Nro de Seccion") final Integer nroSeccion) {
        Seccion se = container.newTransientInstance(Seccion.class);
        se.setDistrito(distrito);
        se.setNroSeccion(nroSeccion);
        se.setDescripcion("");
        container.persistIfNotAlready(se);
        return se;
    }
    public Distrito default0Create() {

        return distritos.listByDNI("Capital Federal").get(0);
    }
//endregion
//region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    Distritos distritos;

//endregion

}
