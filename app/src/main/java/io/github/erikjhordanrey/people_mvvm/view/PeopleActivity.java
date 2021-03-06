/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.erikjhordanrey.people_mvvm.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.github.erikjhordanrey.people_mvvm.R;
import io.github.erikjhordanrey.people_mvvm.data.PeopleFactory;
import io.github.erikjhordanrey.people_mvvm.databinding.PeopleActivityBinding;
import io.github.erikjhordanrey.people_mvvm.viewmodel.PeopleViewModel;
import java.util.Observable;
import java.util.Observer;

public class PeopleActivity extends AppCompatActivity implements Observer {

    private PeopleViewModel peopleViewModel;

    private PeopleActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setSupportActionBar(binding.toolbar);
        setupListPeopleView(binding.recyclerPeople);
        setupObserver(peopleViewModel);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.people_activity);
        peopleViewModel = new PeopleViewModel(this);
        binding.setMainViewModel(peopleViewModel);
    }

    private void setupListPeopleView(RecyclerView recyclerPeople) {
        PeopleAdapter adapter = new PeopleAdapter();
        recyclerPeople.setAdapter(adapter);
        recyclerPeople.setHasFixedSize(true);
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        peopleViewModel.reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_github) {
            startActivityActionView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivityActionView() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PeopleFactory.PROJECT_URL)));
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof PeopleViewModel) {
            PeopleAdapter peopleAdapter = (PeopleAdapter) binding.recyclerPeople.getAdapter();
            PeopleViewModel peopleViewModel = (PeopleViewModel) observable;
            if (peopleAdapter != null) {
                peopleAdapter.setPeopleList(peopleViewModel.getPeopleList());
            }
        }
    }
}
