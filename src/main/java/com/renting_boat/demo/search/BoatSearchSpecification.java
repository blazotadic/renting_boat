package com.renting_boat.demo.search;

import com.renting_boat.demo.entity.Boat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BoatSearchSpecification implements Specification<Boat> {

    private final BoatSearch boatSearch;

    @Override
    public Predicate toPredicate(Root<Boat> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
    {
        List<Predicate> predicates = new ArrayList<>();

        categoryContain(root, criteriaBuilder, predicates);
        priceLess(root, criteriaBuilder, predicates);
        priceGreater(root, criteriaBuilder, predicates);
        freeAfter(root, criteriaBuilder, predicates);
        brandContain(root, criteriaBuilder, predicates);


        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private void brandContain(Root<Boat> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (boatSearch.getBrandContain() != null)
        {
            Predicate brandContain = criteriaBuilder.like(root.get("brand"),
                    "%" + boatSearch.getBrandContain() + "%");
            predicates.add(brandContain);
        }
    }

    private void categoryContain(Root<Boat> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (boatSearch.getCategoryContain() != null)
        {
            Predicate categoryContain = criteriaBuilder.like(root.get("category"),
                    "%" + boatSearch.getCategoryContain() + "%");
            predicates.add(categoryContain);
        }
    }

    private void priceLess(Root<Boat> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (boatSearch.getPriceLess() != null)
        {
            Predicate priceLess = criteriaBuilder.lessThan(root.get("price"),
                     boatSearch.getPriceLess());
            predicates.add(priceLess);
        }
    }

    private void priceGreater(Root<Boat> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (boatSearch.getPriceGreater() != null)
        {
            Predicate priceGreater = criteriaBuilder.greaterThan(root.get("price"),
                    boatSearch.getPriceGreater());
            predicates.add(priceGreater);
        }
    }

    private void freeAfter(Root<Boat> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (boatSearch.getFreeAfter() != null)
        {
            Predicate freeAfter = criteriaBuilder.lessThan(root.get("rentingUntil"),
                    boatSearch.getFreeAfter());
            predicates.add(freeAfter);
        }
    }


}
