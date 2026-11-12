package practice.budget_tracker.expense;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getExpenses(){
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String category){
        return expenseService.getExpensesByCategory(category);
    }

    @PostMapping
    public Expense addExpense(@Valid @RequestBody Expense expense){
        return expenseService.addExpense(expense);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense){
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
    }

}
