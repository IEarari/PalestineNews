package com.ibraheem.android.palestinenews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibraheem.android.palestinenews.MainActivity;
import com.ibraheem.android.palestinenews.NewsArticle;
import com.ibraheem.android.palestinenews.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public
    @Bind(R.id.article_title)
    TextView articleTitle;
    public
    @Bind(R.id.article_section_name)
    TextView articleSectionName;
    public
    @Bind(R.id.article_thumbnail)
    ImageView articleThumbnail;
    private Context mContext;

    public NewsViewHolder(View itemView, Context mContext) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        this.mContext = mContext;
    }

    @Override
    public void onClick(View view) {
        NewsArticle newsArticle = MainActivity.newsArticles.get(getAdapterPosition());
        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(newsArticle.getUrl()));
        mContext.startActivity(browserIntent);
    }
}
