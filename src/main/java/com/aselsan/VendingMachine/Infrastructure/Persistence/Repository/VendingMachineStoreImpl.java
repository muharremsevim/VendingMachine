package com.aselsan.VendingMachine.Infrastructure.Persistence.Repository;

import com.aselsan.VendingMachine.Domain.Model.VendingMachine;
import com.aselsan.VendingMachine.Domain.Port.VendingMachineStore;
import com.aselsan.VendingMachine.Infrastructure.Persistence.Entity.VendingMachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("vendingMachineJpaRepository")
interface VendingMachineJpaRepository extends JpaRepository<VendingMachineEntity, Long> {
    Optional<VendingMachineEntity> findBySerialNumber(String serialNumber);

    @Query("SELECT vm FROM VendingMachineEntity vm LEFT JOIN FETCH vm.products WHERE vm.id = :id")
    Optional<VendingMachineEntity> findByIdWithProducts(@Param("id") Long id);
}

@Component
public class VendingMachineStoreImpl implements VendingMachineStore {
    private final VendingMachineJpaRepository jpaRepository;

    public VendingMachineStoreImpl(VendingMachineJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VendingMachine> findById(Long id) {
        return jpaRepository.findByIdWithProducts(id).map(VendingMachineEntity::toDomain);
    }

    @Override
    public List<VendingMachine> findAll() {
        return jpaRepository.findAll().stream()
                .map(VendingMachineEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public VendingMachine store(VendingMachine vendingMachine) {
        VendingMachineEntity entity = VendingMachineEntity.fromDomain(vendingMachine);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VendingMachine> findBySerialNumber(String serialNumber) {
        return jpaRepository.findBySerialNumber(serialNumber).map(VendingMachineEntity::toDomain);
    }
}