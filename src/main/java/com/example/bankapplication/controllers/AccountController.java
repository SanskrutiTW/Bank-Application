package com.example.bankapplication.controllers;
import com.example.bankapplication.models.Account;
import com.example.bankapplication.models.dto.AccountRequestDTO;
import com.example.bankapplication.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Tag(name = "AccountController", description="Account Operations")
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor // Automatically creates the constructor for the final field

public class AccountController {
    private final AccountService accountService;

    @Operation(
            summary = "Create a new account",
            description="Create a new account for an existing customer with an initial deposit, ensuring it meets the " +
                    "minimum balance requirement for the selected account type."
    )
    @ApiResponses(value={
            @ApiResponse(responseCode="201", description="Account created successfully",
                    content=@Content(
                            mediaType="application/json",
                            schema=@Schema(implementation=Account.class)
                    )
            ),
            @ApiResponse(responseCode="400", description="Invalid Input",
                    content=@Content(
                            mediaType="application/json"
                    )
            ),
            @ApiResponse(responseCode="422", description="Validation failed(deposit below minimum balance)",
                    content=@Content(
                            mediaType="application/json"
                    )
            )
     }
    )
    @PostMapping
    public ResponseEntity<Account> saveAccount(@Valid @RequestBody AccountRequestDTO accountRequest){
        Account savedAccount=accountService.createAccount(accountRequest);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @Operation(summary="Search accounts",description="Fetch all or filter by UUID")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Records fetched successfully",
                    content=@Content(
                            mediaType="application/json",
                            array = @ArraySchema(schema=@Schema(implementation=Account.class))
                    )
            ),
            @ApiResponse(responseCode="404",description="Account not found",
                    content=@Content    //No body for 404 usually
            )
    })
    @GetMapping
    public ResponseEntity<Page<Account>> getAccounts(
            @Valid @Parameter(description = "The unique identifier of the account",
                    example = "550e8400-e29b-41d4-a716-446655440000")
            @RequestParam(required=false) UUID uuid,
            @ParameterObject @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Account> accounts;
        if (uuid != null) {
            accounts=accountService.getAccountByUuidAsList(uuid,pageable);
        }
        else{
            accounts=accountService.getAllAccounts(pageable);
        }
        if(uuid==null && accounts.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary="Deleting a account", description="Soft delete: sets status to INACTIVE")
    @ApiResponses(value={
            @ApiResponse(responseCode="204", description="Deactivated account successfully",content=@Content),
            @ApiResponse(responseCode="404", description="Account does not exist",content=@Content)
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deactivateAccount(@Valid @PathVariable UUID uuid){
        accountService.deactivateAccount(uuid);
        return ResponseEntity.noContent().build();
    }






}
