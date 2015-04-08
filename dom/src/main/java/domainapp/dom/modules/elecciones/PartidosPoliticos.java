package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.value.Blob;
import org.isisaddons.module.excel.dom.ExcelService;

import java.util.List;

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
    @HomePage
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

    @javax.inject.Inject
    DomainObjectContainer container;

    //endregion
}
