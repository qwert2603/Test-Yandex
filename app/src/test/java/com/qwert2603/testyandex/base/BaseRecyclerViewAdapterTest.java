package com.qwert2603.testyandex.base;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qwert2603.testyandex.BaseTest;
import com.qwert2603.testyandex.artist_details.ArtistDetailsPresenter;
import com.qwert2603.testyandex.artist_list.ArtistListAdapter;
import com.qwert2603.testyandex.model.entity.Artist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class BaseRecyclerViewAdapterTest extends BaseTest {

    private RecyclerView mRecyclerView;
    private ArtistListAdapter mArtistListAdapter;
    private ArtistListAdapter.ArtistDetailsViewHolder mViewHolder;

    @Inject
    List<Artist> mArtistList;

    @Inject
    ArtistDetailsPresenter mArtistDetailsPresenter;

    @Inject
    Context mContext;

    @Inject
    BaseRecyclerViewAdapter.ClickCallbacks mClickCallbacks;

    @Inject
    BaseRecyclerViewAdapter.LongClickCallbacks mLongClickCallbacks;

    @Before
    public void setUp() {
        getTestComponent().inject(BaseRecyclerViewAdapterTest.this);

        mArtistListAdapter = new ArtistListAdapter(mArtistList);
        mArtistListAdapter.setClickCallbacks(mClickCallbacks);
        mArtistListAdapter.setLongClickCallbacks(mLongClickCallbacks);

        mRecyclerView = new RecyclerView(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mArtistListAdapter);
        mRecyclerView.measure(1000, 1000);
        mRecyclerView.layout(0, 0, 1000, 1000);

        mViewHolder = mArtistListAdapter.createViewHolder(mRecyclerView, 0);

        Mockito.reset(mArtistDetailsPresenter);
    }

    @Test
    public void testIsShowingList() {
        Assert.assertTrue(mArtistListAdapter.isShowingList(mArtistList));
        Assert.assertFalse(mArtistListAdapter.isShowingList(Arrays.asList(new Artist(), new Artist())));
    }

    @Test
    public void testGetItemCount() {
        Assert.assertEquals(mArtistListAdapter.getItemCount(), mArtistList.size());
    }

    @Test
    public void testOnBindViewHolder() {
        mArtistListAdapter.bindViewHolder(mViewHolder, 0);
        Mockito.verify(mArtistDetailsPresenter).bindView(mViewHolder);
        Mockito.verify(mArtistDetailsPresenter).onViewReady();
    }

    @Test
    public void testOnViewRecycled() {
        mArtistListAdapter.bindViewHolder(mViewHolder, 0);
        mArtistListAdapter.onViewRecycled(mViewHolder);
        Mockito.verify(mArtistDetailsPresenter).onViewNotReady();
        Mockito.verify(mArtistDetailsPresenter).unbindView();
    }

    @Test
    public void testOnFailedToRecycleView() {
        mArtistListAdapter.bindViewHolder(mViewHolder, 0);
        mArtistListAdapter.onFailedToRecycleView(mViewHolder);
        Mockito.verify(mArtistDetailsPresenter).onViewNotReady();
        Mockito.verify(mArtistDetailsPresenter).unbindView();
    }

    @Test
    public void testOnItemClicked() {
        int position = 1;
        mArtistListAdapter.bindViewHolder(mViewHolder, position);
        mViewHolder.mItemView.performClick();
        Mockito.verify(mClickCallbacks).onItemClicked(position);
    }

    @Test
    public void testOnItemLongClicked() {
        int position = 0;
        mArtistListAdapter.bindViewHolder(mViewHolder, position);
        mViewHolder.mItemView.performLongClick();
        Mockito.verify(mLongClickCallbacks).onItemLongClicked(position);
    }

    @Test
    public void testSetSelectedItemPosition() {
        ArtistListAdapter.ArtistDetailsViewHolder viewHolder0;
        ArtistListAdapter.ArtistDetailsViewHolder viewHolder1;
        viewHolder0 = (ArtistListAdapter.ArtistDetailsViewHolder) mRecyclerView.findViewHolderForLayoutPosition(0);
        viewHolder1 = (ArtistListAdapter.ArtistDetailsViewHolder) mRecyclerView.findViewHolderForLayoutPosition(1);
        Assert.assertFalse(viewHolder0.mItemView.isSelected());
        Assert.assertFalse(viewHolder1.mItemView.isSelected());

        mArtistListAdapter.setSelectedItemPosition(0);
        mRecyclerView.measure(1000, 1000);
        mRecyclerView.layout(0, 0, 1000, 1000);
        viewHolder0 = (ArtistListAdapter.ArtistDetailsViewHolder) mRecyclerView.findViewHolderForLayoutPosition(0);
        viewHolder1 = (ArtistListAdapter.ArtistDetailsViewHolder) mRecyclerView.findViewHolderForLayoutPosition(1);
        Assert.assertTrue(viewHolder0.mItemView.isSelected());
        Assert.assertFalse(viewHolder1.mItemView.isSelected());

        mArtistListAdapter.setSelectedItemPosition(1);
        mRecyclerView.measure(1000, 1000);
        mRecyclerView.layout(0, 0, 1000, 1000);
        viewHolder0 = (ArtistListAdapter.ArtistDetailsViewHolder) mRecyclerView.findViewHolderForLayoutPosition(0);
        viewHolder1 = (ArtistListAdapter.ArtistDetailsViewHolder) mRecyclerView.findViewHolderForLayoutPosition(1);
        Assert.assertFalse(viewHolder0.mItemView.isSelected());
        Assert.assertTrue(viewHolder1.mItemView.isSelected());
    }

}
