package com.example.customrecyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_view.view.*

class MyAddapter(var myDataClass : List<MyDataClass>, val context: Context)
    : RecyclerView.Adapter<MyAddapter.MyViewHolder>(), Filterable {

    var myDataClassForFiltering = listOf<MyDataClass>()

    init {
        myDataClassForFiltering = myDataClass
    }

    ///////////////////////
//    companion object {
//        var mySerialNUm : String? = ""
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_view,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /////////////////////
       // mySerialNUm = holder.itemId.toString()

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