package practice.budget_tracker.expense;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import practice.budget_tracker.exceptions.BadRequestException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        if(expenses.isEmpty()){
            throw new EntityNotFoundException("No expenses found");
        }
        return expenses;
    }

    public List<Expense> getExpensesByCategory(String category) {
        List<Expense> expenses = expenseRepository.getExpensesByCategoryIgnoreCase(category);
        if(expenses.isEmpty()){
            throw new EntityNotFoundException("No expenses with category \""+ category + "\" found");
        }
        return expenses;
    }

    public Expense addExpense(Expense expense){

        if(expense.getDescription() == null || expense.getDescription().isBlank()){
            throw new BadRequestException("Expense description is required");
        }

        if(expense.getAmount() == null || expense.getAmount().compareTo(BigDecimal.ZERO) <= 0){
           throw new BadRequestException("Amount must be greater than 0");
        }

        if(expense.getDueDate() == null){
            throw new BadRequestException("Due date is required");
        }

        if(expense.getCategory() == null || expense.getCategory().isBlank()){
            expense.setCategory("Expense");
        }

        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expense) {

        if(!expenseRepository.existsById(id)){
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
        Expense expenseToUpdate = expenseRepository.findById(id).get();

        if(expense.getDescription() != null && !expense.getDescription().isBlank()){
            expenseToUpdate.setDescription(expense.getDescription());
        }

        if(expense.getCategory() != null && !expense.getCategory().isBlank()){
            expenseToUpdate.setCategory(expense.getCategory());
        }

        if(expense.getAmount() != null && expense.getAmount().compareTo(BigDecimal.ZERO) > 0 ){
            expenseToUpdate.setAmount(expense.getAmount());
        }

        if(expense.getDueDate() != null){
            expenseToUpdate.setDueDate(expense.getDueDate());
        }

        return expenseRepository.save(expenseToUpdate);
    }

    public void deleteExpense(Long id) {
        if(!expenseRepository.existsById(id)){
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
        expenseRepository.deleteById(id);
    }

}
