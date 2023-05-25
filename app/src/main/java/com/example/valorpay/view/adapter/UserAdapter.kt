
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorpay.R
import com.example.valorpay.databinding.UserItemsBinding
import com.example.valorpay.model.UserModel
import com.example.valorpay.util.AppConstant


class UserAdapter(private val userList: UserModel,
                  private val buildContext: Context, private val onItemClickListener: ItemClickListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: UserItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.binding.name.text =userList[p1].name
        holder.binding.email.text = userList[p1].email

        Glide.with(buildContext)
            .load(AppConstant.SampleImageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.binding.userImage)
        holder.binding.cardUser.setOnClickListener {
            onItemClickListener.onItemClick(p1)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }}