package com.example.todolistappsqlite; // 본인의 패키지 이름으로 변경

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final List<TodoItem> todoList;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private final TodoDatabaseHelper dbHelper; // DatabaseHelper 인스턴스 추가

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    // MainActivity에서 DatabaseHelper를 전달받는 생성자
    public TodoAdapter(List<TodoItem> todoList, TodoDatabaseHelper dbHelper) {
        this.todoList = todoList;
        this.dbHelper = dbHelper;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem currentItem = todoList.get(position);
        holder.textViewTodo.setText(currentItem.getTask());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        holder.textViewRegistrationDate.setText(dateFormat.format(currentItem.getRegistrationDate()));

        holder.checkBoxCompleted.setChecked(currentItem.isCompleted());
        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentItem.setCompleted(isChecked);
            // 어댑터에서 직접 데이터베이스 업데이트 (MainActivity에서 dbHelper를 전달받은 경우)
            if (dbHelper != null) {
                dbHelper.updateTodo(currentItem);
            }
        });

        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(position));
        }

        if (itemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                itemLongClickListener.onItemLongClick(position);
                return true; // 롱클릭 이벤트 소비
            });
        }
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTodo;
        TextView textViewRegistrationDate;
        CheckBox checkBoxCompleted;

        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTodo = itemView.findViewById(R.id.textViewTodo);
            textViewRegistrationDate = itemView.findViewById(R.id.textViewRegistrationDate);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        }
    }
}