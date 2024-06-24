import store from "@/store";

const searchService = {
  filterListings(filter) {
    var listings = store.getters.getAllCompanyListings;
    return listings.filter(listing => {
      if (filter.companyName && listing.companyname !== filter.companyName) {
        return false;
      }
      if (filter.industry && listing.category !== filter.industry) {
        return false;
      }
      if (filter.shareType && listing.shareType !== filter.shareType) {
        return false;
      }
      if (
        filter.priceRange.minimum !== null &&
        listing.price < filter.priceRange.minimum
      ) {
        return false;
      }
      if (
        filter.priceRange.maximum !== null &&
        listing.price > filter.priceRange.maximum
      ) {
        return false;
      }
      return true;
    });
  },

}

export default searchService