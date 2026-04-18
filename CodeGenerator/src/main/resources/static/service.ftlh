package [=model.uri].service;

import [=model.uri].dao.[=model.name?cap_first]Dao;
import [=model.uri].entities.[=model.name?cap_first];
import [=model.uri].repository.[=model.name?cap_first]Repository;
import [=model.uri].mapper.[=model.name?cap_first]Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class [=model.name?cap_first]Service {
    private final [=model.name?cap_first]Repository [=model.name?uncap_first]Repository;
    private final [=model.name?cap_first]Mapper [=model.name?uncap_first]Mapper;

    public [=model.name?cap_first]Service([=model.name?cap_first]Repository [=model.name?uncap_first]Repository, [=model.name?cap_first]Mapper [=model.name?uncap_first]Mapper) {
        this.[=model.name?uncap_first]Repository = [=model.name?uncap_first]Repository;
        this.[=model.name?uncap_first]Mapper = [=model.name?uncap_first]Mapper;
    }

    public Page<[=model.name?cap_first]Dao> get[=model.name?cap_first]Page(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize[#if model.sortBy??], Sort.by(Sort.Direction.[=(model.sortBy.direction)!"ASC"],"[=model.sortBy.propertyName]")[/#if]);
        Page<[=model.name?cap_first]> entityPage = [=model.name?uncap_first]Repository.findAll(pageable);
        return entityPage.map(entity -> [=model.name?uncap_first]Mapper.toDao(entity));
    }

    public void save[=model.name?cap_first]([=model.name?cap_first]Dao dao) {
        [=model.name?cap_first] entity = [=model.name?uncap_first]Mapper.toEntity(dao);
        [=model.name?uncap_first]Repository.save(entity);
    }

    public Optional<[=model.name?cap_first]Dao> find[=model.name?cap_first]ById(int id) {
        return [=model.name?uncap_first]Repository.findById(id)
                .map(entity -> [=model.name?uncap_first]Mapper.toDao(entity));
    }

    public void delete[=model.name?cap_first](Optional<Integer> id) {
        id.ifPresent(integer -> [=model.name?uncap_first]Repository.deleteById(id.get()));
    }
	
	public List<[=model.name?cap_first]Dao> get[=model.name?cap_first]List(){
		return [=model.name?uncap_first]Mapper.toDaoList([=model.name?uncap_first]Repository.findAll());
	}
}