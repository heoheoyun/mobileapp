package com.example.todolistappva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private final Context context;
    private final List<TodoItem> todos;
    private final TodoDatabaseHelper dbHelper;

    public TodoAdapter(Context context, List<TodoItem> todos, TodoDatabaseHelper dbHelper) {
        this.context = context;
        this.todos = todos;
        this.dbHelper = dbHelper;
    }

    /**
     * 외부에서 아이템 리스트를 수정할 때 사용
     */
    public List<TodoItem> getTodos() {
        return todos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem todo = todos.get(position);
        holder.textViewTodo.setText(todo.getTodoText());
        holder.checkBoxCompleted.setChecked(false);

        holder.buttonEdit.setOnClickListener(v -> {
            EditText input = new EditText(context);
            input.setText(todo.getTodoText());
            new AlertDialog.Builder(context)
                    .setTitle(R.string.edit)
                    .setView(input)
                    .setPositiveButton(R.string.save, (dialog, which) -> {
                        String newText = input.getText().toString().trim();
                        if (!newText.isEmpty()) {
                            todo.setTodoText(newText);
                            dbHelper.updateTodo(todo);
                            notifyItemChanged(position);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        });

        holder.buttonDelete.setOnClickListener(v -> {
            dbHelper.deleteTodo(todo.getId());
            todos.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final CheckBox checkBoxCompleted;
        final TextView textViewTodo;
        final ImageButton buttonEdit;
        final ImageButton buttonDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
            textViewTodo = itemView.findViewById(R.id.textViewTodo);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}