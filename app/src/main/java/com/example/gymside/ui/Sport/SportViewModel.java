//package com.example.gymside.ui.Sport;
//
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MediatorLiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Transformations;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.gymside.domain.Sport;
//import com.example.gymside.repository.SportRepository;
//import com.example.gymside.viewmodel.RepositoryViewModel;
//import com.example.gymside.vo.AbsentLiveData;
//import com.example.gymside.vo.Resource;
//import com.example.gymside.vo.Status;
//
//public class SportViewModel extends RepositoryViewModel<SportRepository> {
//
//    private final static int PAGE_SIZE = 10;
//
//    private int sportPage = 0;
//    private boolean isLastSportPage = false;
//    private final List<Sport> allSports = new ArrayList<>();
//    private final MediatorLiveData<Resource<List<Sport>>> sports = new MediatorLiveData<>();
//    private final MutableLiveData<Integer> sportId = new MutableLiveData<>();
//    private final LiveData<Resource<Sport>> sport;
//    private final MediatorLiveData<Resource<Sport>> addSport = new MediatorLiveData<>();
//
//    public SportViewModel(SportRepository repository) {
//        super(repository);
//
//        sport = Transformations.switchMap(sportId, sportId -> {
//            if (sportId == null) {
//                return AbsentLiveData.create();
//            } else {
//                return repository.getSport(sportId);
//            }
//        });
//    }
//
//    public LiveData<Resource<List<Sport>>> getSports() {
//        getMoreSports();
//        return sports;
//    }
//
//    public void getMoreSports() {
//        if (isLastSportPage)
//            return;
//
//        sports.addSource(repository.getSports(sportPage, PAGE_SIZE), resource -> {
//            if (resource.status == Status.SUCCESS) {
//                if ((resource.data.size() == 0) || (resource.data.size() < PAGE_SIZE))
//                    isLastSportPage = true;
//
//                sportPage++;
//
//                allSports.addAll(resource.data);
//                sports.setValue(Resource.success(allSports));
//            } else if (resource.status == Status.LOADING) {
//                sports.setValue(resource);
//            }
//        });
//    }
//
//    public LiveData<Resource<Sport>> getSport() {
//        return sport;
//    }
//
//    public LiveData<Resource<Sport>> addSport(Sport sport) {
//        return repository.addSport(sport);
//    }
//
//    public LiveData<Resource<Sport>> modifySport(Sport sport) {
//        return repository.modifySport(sport);
//    }
//
//    public LiveData<Resource<Void>> deleteSport(Sport sport) {
//        return repository.deleteSport(sport);
//    }
//
//    public void setSportId(int sportId) {
//        if ((this.sportId.getValue() != null) &&
//                (sportId == this.sportId.getValue())) {
//            return;
//        }
//
//        this.sportId.setValue(sportId);
//    }
//}
