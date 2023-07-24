package id.simian.oauth2.repository;

import id.simian.oauth2.entity.ProjectOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectOrderRepository extends PagingAndSortingRepository<ProjectOrder, Long>, JpaSpecificationExecutor<ProjectOrder> {
}
