package application.controllers;

import application.dtos.request.CreateCompanyClientRequest;
import application.dtos.request.CreateNaturalPersonRequest;
import application.dtos.request.UpdateContactRequest;
import application.dtos.response.ApiResponse;
import application.dtos.response.ClientResponse;
import application.usecases.CreateCompanyClientUseCase;
import application.usecases.CreateNaturalPersonUseCase;
import domain.models.CompanyClient;
import domain.models.NaturalPerson;
import domain.ports.ConsultClientInformationPort;
import domain.ports.UpdateClientContactInformationPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final CreateNaturalPersonUseCase createNaturalPersonUseCase;
    private final CreateCompanyClientUseCase createCompanyClientUseCase;
    private final ConsultClientInformationPort consultClientPort;
    private final UpdateClientContactInformationPort updateContactPort;

    public ClientController(
            CreateNaturalPersonUseCase createNaturalPersonUseCase,
            CreateCompanyClientUseCase createCompanyClientUseCase,
            ConsultClientInformationPort consultClientPort,
            UpdateClientContactInformationPort updateContactPort
    ) {
        this.createNaturalPersonUseCase = createNaturalPersonUseCase;
        this.createCompanyClientUseCase = createCompanyClientUseCase;
        this.consultClientPort = consultClientPort;
        this.updateContactPort = updateContactPort;
    }

    // POST /api/clients/natural-person
    @PostMapping("/natural-person")
    public ResponseEntity<ApiResponse<Void>> createNaturalPerson(
            @RequestBody CreateNaturalPersonRequest request
    ) {
        NaturalPerson person = NaturalPerson.create(
                request.getIdentification(),
                request.getFullName(),
                request.getBirthDate()
        );
        if (request.getEmail() != null) {
            person.updateContactInfo(request.getEmail(), request.getPhone(), request.getAddress());
        }
        createNaturalPersonUseCase.execute(person);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Cliente persona natural creado exitosamente"));
    }

    // POST /api/clients/company
    @PostMapping("/company")
    public ResponseEntity<ApiResponse<Void>> createCompany(
            @RequestBody CreateCompanyClientRequest request
    ) {
        CompanyClient company = CompanyClient.create(request.getTaxId(), request.getCompanyName());
        if (request.getEmail() != null) {
            company.updateContactInfo(request.getEmail(), request.getPhone(), request.getAddress());
        }
        createCompanyClientUseCase.execute(company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Cliente empresa creado exitosamente"));
    }

    // GET /api/clients/{clientId}
    @GetMapping("/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponse>> getClient(@PathVariable String clientId) {
        ClientResponse response = ClientResponse.from(consultClientPort.execute(clientId));
        return ResponseEntity.ok(ApiResponse.ok("Cliente encontrado", response));
    }

    // PUT /api/clients/{clientId}/contact
    @PutMapping("/{clientId}/contact")
    public ResponseEntity<ApiResponse<Void>> updateContact(
            @PathVariable String clientId,
            @RequestBody UpdateContactRequest request
    ) {
        updateContactPort.execute(clientId, request.getEmail(), request.getPhone(), request.getAddress());
        return ResponseEntity.ok(ApiResponse.ok("Información de contacto actualizada"));
    }
}
