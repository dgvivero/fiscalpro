package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;

import java.util.List;

/**
 * Created by German on 05/04/2015.
 */
@DomainService(repositoryFor = MesaElectoral.class)
@DomainServiceLayout(menuOrder = "50")
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

    @javax.inject.Inject
    DomainObjectContainer container;

    //endregion
}
