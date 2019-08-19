package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;
import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersion;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@NamedEntityGraph(
        name = "invoiceWithInvoiceRunAndClientAndPlanVersion",
        attributeNodes = {
                @NamedAttributeNode(value = "invoiceRun"),
                @NamedAttributeNode(value = "client", subgraph = "meter"),
                @NamedAttributeNode(value = "planVersion", subgraph = "plan"),
                @NamedAttributeNode(value = "invoiceLines")
        },
        subgraphs = {
                @NamedSubgraph(name = "meter", attributeNodes = @NamedAttributeNode(value = "meter")),
                @NamedSubgraph(name = "plan", attributeNodes = @NamedAttributeNode("plan"))
        }
)
public class Invoice extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "invoice_id_seq", sequenceName = "invoice_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "invoice_id_seq")
    private Long id;

    @NotNull
    @Column(length = 255)
    private String number;

    @NotNull
    private ZonedDateTime issueDate;

    @ManyToOne
    @JoinColumn(name = "invoice_run_id")
    private InvoiceRun invoiceRun;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "plan_version_id", nullable = false)
    private PlanVersion planVersion;

    @NotNull
    private BigDecimal grossTotal;

    @OneToMany
    @JoinColumn(name = "invoice_id")
    private List<InvoiceLine> invoiceLines;

    protected Invoice() {
    }

    public Invoice(String number, InvoiceRun invoiceRun, Client client, PlanVersion planVersion, BigDecimal grossTotal) {
        this.number = number;
        this.issueDate = invoiceRun.getIssueDate();
        this.invoiceRun = invoiceRun;
        this.client = client;
        this.planVersion = planVersion;
        this.grossTotal = grossTotal;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public InvoiceRun getInvoiceRun() {
        return invoiceRun;
    }

    public Client getClient() {
        return client;
    }

    public PlanVersion getPlanVersion() {
        return planVersion;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }
}
