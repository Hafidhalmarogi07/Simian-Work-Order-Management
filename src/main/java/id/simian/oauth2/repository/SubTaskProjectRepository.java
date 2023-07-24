package id.simian.oauth2.repository;

import id.simian.oauth2.entity.SubTaskProject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubTaskProjectRepository extends PagingAndSortingRepository<SubTaskProject, Long>, JpaSpecificationExecutor<SubTaskProject> {
}
