package com.example.customrecyclerview

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.Editable
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_dialog_add_update.view.*
import kotlinx.android.synthetic.main.items_view.view.*

class MyAddapter(var myDataClass : ArrayList<MyDataClass>, val context: Context)
    : RecyclerView.Adapter<MyAddapter.MyViewHolder>(), Filterable {

    var myDataClassForFiltering = listOf<MyDataClass>()

    init {
        myDataClassForFiltering = myDataClass
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_view,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentPossition = myDataClass[position]

        holder.image.setImageResource(currentPossition.images)
        holder.title.text = currentPossition.title
        holder.description.text = currentPossition.description
        holder.serialNum.text = holder.absoluteAdapterPosition.toString()
        holder.bind(myDataClassForFiltering.get(position))

        // sending the above data to itemsDetailScreen Activity.
        holder.myLayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ItemsDetailScreen::class.java)
            intent.putExtra("imageKey", currentPossition.images)
            intent.putExtra("titleKey", currentPossition.title)
            intent.putExtra("descriptionKey", currentPossition.description)
            context.startActivity(intent)
        })
        // Long Press Action
        holder.myLayout.setOnLongClickListener {

            val customDialog = View.inflate(context, R.layout.custom_dialog_add_update, null)
            val mAlertDialog = AlertDialog.Builder(context)
            mAlertDialog.setView(customDialog).show()
            customDialog.btn_cus_dia_add_update.text = "Update"
            customDialog.btn_cus_cancel.text = "Delete"

            // Required Extension function for string to Editable text
            fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)

            customDialog.cus_edit_text_title.text = myDataClass[position].title.toEditable()
            customDialog.cus_edit_text_description.text = myDataClass[position].description.toEditable()

            // Updata Button
            customDialog.btn_cus_dia_add_update.setOnClickListener {

                val mTitle = customDialog.cus_edit_text_title.text.toString().trim()
                val mDescription = customDialog.cus_edit_text_description.text.toString().trim()

                myDataClass.set(position, MyDataClass(R.drawable.car, mTitle, mDescription))
                notifyItemChanged(position)
            }
            // Cancel Button
            customDialog.btn_cus_cancel.setOnClickListener {
                myDataClass.removeAt(position)
                notifyItemChanged(position)
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
       return myDataClassForFiltering.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serialNum : TextView = view.findViewById(R.id.serial_number)
        val image : ImageView = view.findViewById(R.id.image_view)
        val title:  TextView = view.findViewById(R.id.tv_title)
        val description : TextView = view.findViewById(R.id.tv_description)
        val myLayout : RelativeLayout = view.findViewById(R.id.my_relative_layout)

        fun bind(model: MyDataClass): Unit {
            ////////////////////////
            //itemView.serial_number.text = mySerialNUm
            itemView.tv_title.text = model.title
            itemView.tv_description.text = model.description
            itemView.image_view.setImageResource(model.images)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
               val charSearch = charSequence.toString()
                if (charSearch.isEmpty()) {
                    myDataClassForFiltering = myDataClass
                } else {
                    val resultList = ArrayList<MyDataClass>()
                    for (row in myDataClass) {
                        if (row.title.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    myDataClassForFiltering = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = myDataClassForFiltering
                return  filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1?.values != null) {
                    myDataClassForFiltering = p1?.values as List<MyDataClass>
                    notifyDataSetChanged()
                } else {
                    myDataClassForFiltering = myDataClass
                }
            }

        }
    }
}