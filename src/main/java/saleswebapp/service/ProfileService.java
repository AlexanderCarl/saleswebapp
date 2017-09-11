package saleswebapp.service;


import saleswebapp.components.ProfileForm;
import saleswebapp.repository.impl.SalesPerson;

/**
 * Created by Alexander Carl on 11.09.2017.
 */
public interface ProfileService {

    void addSalesPersonToTransactionStore(SalesPerson salesPerson);

    boolean salesPersonHasBeenAlteredMeanwhile(int salesPersonId);

    void saveSalesPerson(ProfileForm profileForm);

    SalesPerson getSalesByEmail(String email);

    boolean emailOfSalesPersonHasBeenAltered(int salesPersonId);

    void setNewPassword(ProfileForm profileForm);
}
