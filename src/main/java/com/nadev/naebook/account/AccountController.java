package com.nadev.naebook.account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.auth.LoginUser;
import com.nadev.naebook.account.dto.AccountRequestDto;
import com.nadev.naebook.account.dto.RelationRequestDto;
import com.nadev.naebook.account.dto.TagRequestDto;
import com.nadev.naebook.account.model.AccountModel;
import com.nadev.naebook.account.model.AccountTagModel;
import com.nadev.naebook.account.model.RelationModel;
import com.nadev.naebook.common.ResponseDto;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.AccountTag;
import com.nadev.naebook.domain.Relation;
import com.nadev.naebook.exception.ForbiddenException;
import com.nadev.naebook.exception.NotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AccountTagRepository accountTagRepository;
    private final RelationRepository relationRepository;

    @GetMapping("/{id}")
    public ResponseEntity accountById(@PathVariable Long id) {
        return findAccountById(id);
    }

    @GetMapping("/me")
    public ResponseEntity accountMe(@LoginUser Account account) {
        return findAccountById(account.getId());
    }

    private ResponseEntity<?> findAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(NotFoundException::new);
        AccountModel model = new AccountModel(account);
        model.add(
            linkTo(methodOn(AccountController.class).accountById(id)).withRel("update-account"));
        return ResponseEntity.ok(model);
    }

    @PutMapping
    public ResponseEntity updateAccount(
        @LoginUser Account account,
        @RequestBody @Valid AccountRequestDto requestDto,
        BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Account newAccount = accountService.updateAccount(requestDto, account.getId());
        return ResponseEntity.ok(new AccountModel(newAccount));
    }

    @PostMapping("/tags")
    public ResponseEntity createAccountTag(@LoginUser Account account,
        @RequestBody TagRequestDto requestDto) {
        AccountTag accountTag = accountService
            .createAccountTag(account.getId(), requestDto.getTitle());
        URI uri = linkTo(methodOn(AccountController.class)
            .findAccountTag(accountTag.getId()))
            .withSelfRel().toUri();
        return ResponseEntity.created(uri).body(new AccountTagModel(accountTag));
    }

    @GetMapping("/tags/{tagId}")
    public ResponseEntity findAccountTag(@PathVariable Long tagId) {
        AccountTag findAccountTag = accountTagRepository.findById(tagId)
            .orElseThrow(NotFoundException::new);
        AccountTagModel model = new AccountTagModel(findAccountTag);
        model.add(linkTo(methodOn(AccountController.class).findAccountTag(tagId))
            .withRel("remove-accountTag"));
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity deleteAccountTag(@LoginUser Account account, @PathVariable Long tagId) {
        AccountTag findTag = accountTagRepository.findById(tagId)
            .orElseThrow(NotFoundException::new);
        Long accountId = findTag.getAccount().getId();
        if (!accountId.equals(account.getId())) {
            throw new ForbiddenException();
        }
        accountTagRepository.delete(findTag);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/tags")
    public ResponseEntity findLoginAccountTag(@LoginUser Account account) {
        List<AccountTag> accountTags = accountTagRepository.findAllByAccount(account.getId());
        Set<AccountTagModel> models = accountTags.stream()
            .map(AccountTagModel::new)
            .collect(Collectors.toSet());
        return ResponseEntity.ok(new ResponseDto<>(models));
    }

    @GetMapping("/{accountId}/tags")
    public ResponseEntity findAccountTagByAccountId(@PathVariable Long accountId) {
        accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        List<AccountTag> accountTag = accountTagRepository.findAllByAccount(accountId);
        Set<AccountTagModel> models = accountTag.stream()
            .map(AccountTagModel::new)
            .collect(Collectors.toSet());
        return ResponseEntity.ok(new ResponseDto<>(models));
    }

    @GetMapping("/relation/{id}")
    public ResponseEntity findRelation(@PathVariable Long id) {
        Relation relation = relationRepository.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(new RelationModel(relation));
    }

    @PostMapping("/follow")
    public ResponseEntity followAccount(@LoginUser Account account,
        @RequestBody RelationRequestDto requestDto) {
        Relation relation = accountService
            .followAccount(account.getId(), requestDto.getFolloweeId());
        URI uri = linkTo(methodOn(AccountController.class)
            .findRelation(relation.getId()))
            .withSelfRel().toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity unfollowAccount(@LoginUser Account account,
        @RequestBody RelationRequestDto requestDto) {
        accountService.unfollowAccount(account.getId(), requestDto.getFolloweeId());
        return ResponseEntity.ok().build();
    }
}
