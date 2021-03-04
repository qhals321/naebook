package com.nadev.naebook.account.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.AccountController;
import com.nadev.naebook.domain.Relation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

@Getter @Setter
public class RelationModel extends EntityModel<Relation> {

    private Long followeeId;
    private Long followerId;

    public RelationModel(Relation relation) {
        this.followeeId = relation.getFollowee().getId();
        this.followerId = relation.getFollower().getId();
        add(linkTo(methodOn(AccountController.class).findRelation(relation.getId())).withSelfRel());
    }
}
