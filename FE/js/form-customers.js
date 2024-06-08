$(document).ready(function () {
  $('#dataTable').DataTable();
});

var table = $('#dataTable').DataTable();
var listArea = [];
var listMeterReadingAll = [];
var listMeterReading = [];
var hasDisabledInput = false;
var districtDataLoaded = false;

setInterval(function () {
  table.rows().every(function () {
      var inputField = $(this.node()).find('input[type="text"]');
      if (!inputField.prop('disabled')) {
          hasDisabledInput = true;
          return false;
      }
  });

  if (hasDisabledInput) {
      $('.confirm-and-save').prop('disabled', false);
  } else {
      $('.confirm-and-save').prop('disabled', true);
  }
}, 500);

document.addEventListener('DOMContentLoaded', function () {
  var employee = localStorage.getItem('employee');
  listMeter = [];
  fetch('http://localhost:8080/api/areas/filter', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: employee
  })
      .then(response => response.json())
      .then(data => {
          listArea = data;

          var wardCommune = document.querySelector('.ward-commune');
          var district = document.querySelector('.district');
          var city = document.querySelector('.city');
          var addedPlaceholderWardCommune = false;
          var addedPlaceholderDistrict = false;
          var addedPlaceholderCity = false;

          data.forEach(item => {
              if (!addedPlaceholderWardCommune && !Array.from(wardCommune.options).some(option => option.text === item.wardCommune)) {
                  var option1 = document.createElement('option');
                  option1.text = "Vui lòng chọn xã";
                  option1.disabled = true;
                  option1.selected = true;
                  wardCommune.appendChild(option1);
                  addedPlaceholderWardCommune = true;
              }

              if (!addedPlaceholderDistrict && !Array.from(district.options).some(option => option.text === item.district)) {
                  var option2 = document.createElement('option');
                  option2.text = "Vui lòng chọn huyện";
                  option2.selected = true;
                  option2.disabled = true;
                  district.appendChild(option2);
                  addedPlaceholderDistrict = true;
              }

              if (!addedPlaceholderCity && !Array.from(city.options).some(option => option.text === item.city)) {
                  var option3 = document.createElement('option');
                  option3.text = "Vui lòng chọn thành phố";
                  city.appendChild(option3);
                  addedPlaceholderCity = true;
              }
          });
      })
      .catch(error => console.error('Error:', error));
});

document.querySelector('.city').addEventListener('click', function () {
  var nameCity = document.querySelector(".city");
  nameCity.innerHTML = "";
  var addedCities = [];
  listArea.forEach(function (item) {
      if (!addedCities.includes(item.city)) {
          var option = document.createElement("option");
          option.text = item.city;
          option.value = item.city;
          nameCity.add(option);
          addedCities.push(item.city);
      }
  });
  var wardCommune = document.querySelector('.ward-commune');
  var district = document.querySelector('.district');
  district.innerHTML = "";
  var option2 = document.createElement('option');
  option2.text = "Vui lòng chọn huyện";
  option2.selected = true;
  option2.disabled = true;
  district.append(option2);
  wardCommune.innerHTML = "";
  var option1 = document.createElement('option');
  option1.text = "Vui lòng chọn xã";
  option1.disabled = true;
  option1.selected = true;
  wardCommune.appendChild(option1);
  districtDataLoaded = false;
});

document.querySelector('.district').addEventListener('click', function () {
  var nameCitys = document.querySelector(".city").value;
  var districtList = getListDistrictByNameCity(nameCitys);
  var districtSelect = document.querySelector('.district');

  if (!districtDataLoaded && nameCitys !== "Vui lòng chọn thành phố") {
      districtSelect.disabled = false;
      districtSelect.innerHTML = "";
      districtList.forEach(function (district) {
          var option = document.createElement("option");
          option.text = district;
          option.value = district;
          districtSelect.add(option);
      });

      districtDataLoaded = true;
  }
  var wardCommune = document.querySelector('.ward-commune');
  wardCommune.innerHTML = "";
  var option1 = document.createElement('option');
  option1.text = "Vui lòng chọn xã";
  option1.disabled = true;
  option1.selected = true;
  wardCommune.appendChild(option1);
});

document.querySelector('.ward-commune').addEventListener('click', function () {
  var nameDistricts = document.querySelector(".district").value;
  var wardCommunetList = getListwardCommuneByNameDistrict(nameDistricts);
  var wardCommunetSelect = document.querySelector('.ward-commune');

  if (nameDistricts !== "Vui lòng chọn huyện") {
      wardCommunetSelect.disabled = false;
      wardCommunetSelect.innerHTML = "";
      wardCommunetList.forEach(function (wardCommune) {
          var option = document.createElement("option");
          option.text = wardCommune;
          option.value = wardCommune;
          wardCommunetSelect.add(option);
      });
  }
});

function getListDistrictByNameCity(nameCity) {
  return listArea.filter(area => area.city === nameCity).map(area => area.district);
}

function getListwardCommuneByNameDistrict(nameDistrict) {
  return listArea.filter(area => area.district === nameDistrict).map(area => area.wardCommune);
}

var selectedFullName = '';

fetch('http://localhost:8080/api/user/fullNames', {
  method: 'GET',
})
  .then(response => {
      if (!response.ok) {
          throw new Error('Failed to fetch customer names');
      }
      return response.json();
  })
  .then(data => {
      var dropdown = document.querySelector('.customerName');
      dropdown.innerHTML = "";

      data.forEach(function (customerName) {
          var option = document.createElement('option');
          option.value = customerName;
          option.text = customerName;
          dropdown.appendChild(option);
      });

      dropdown.addEventListener('change', function () {
          selectedFullName = this.value;
          console.log('Giá trị đã chọn:', selectedFullName);
      });
  })
  .catch(error => {
      console.error('Đã xảy ra lỗi:', error);
      alert('Có lỗi xảy ra khi gửi yêu cầu đến server.');
  });

document.querySelector('.add-members').addEventListener('click', function () {
  var customerName = document.querySelector('.customerName').value;
  var wardCommune = document.querySelector('.ward-commune').value;
  var district = document.querySelector('.district').value;
  var city = document.querySelector('.city').value;
  // var selectedArea = listArea.find(area => area.wardCommune === wardCommune && area.district === district && area.city === city);

  if (city != null) {
      var meterCode = document.getElementById('meterCode').value;
      var meterType = document.getElementById('meterType').value;
      // var previousReading = document.getElementById('previousReading').value;
      var installmentDate = document.getElementById('updateDate').value;
      var installmentAddress = document.getElementById('installment_address').value;

      var customerData = { 
        wardCommune, 
        customerName,
        meterCode,
        meterType, 
        installmentAddress, 
        installmentDate, 
      };

      console.log('Customer Data:', customerData);

      // Gửi dữ liệu đến API
      fetch('http://localhost:8080/api/meters/save', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(customerData)
      })
      .then(response => response.json())
      .then(data => {
          console.log('Success:', data);
          alert('Khách hàng đã được thêm thành công.');
          // Xử lý thành công, có thể cập nhật giao diện hoặc xóa dữ liệu form
      })
      .catch(error => {
          console.error('Error:', error);
          alert('Có lỗi xảy ra khi thêm khách hàng.');
      });

  } else {
      alert('Vui lòng chọn đầy đủ thông tin khu vực.');
  }
});
